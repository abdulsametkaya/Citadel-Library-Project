package com.library.citadel_library.service;

import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.domain.Role;
import com.library.citadel_library.domain.User;
import com.library.citadel_library.domain.enums.RoleType;
import com.library.citadel_library.dto.UserCreateDTO;
import com.library.citadel_library.dto.UserDTO;
import com.library.citadel_library.dto.mapper.UserMapper;
import com.library.citadel_library.dto.requests.*;
import com.library.citadel_library.dto.response.LoanResponse;
import com.library.citadel_library.exception.BadRequestException;
import com.library.citadel_library.exception.ConflictException;
import com.library.citadel_library.exception.ResourceNotFoundException;
import com.library.citadel_library.exception.message.ErrorMessage;
import com.library.citadel_library.repository.LoanRepository;
import com.library.citadel_library.repository.RoleRepository;
import com.library.citadel_library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private LoanRepository loanRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;


    public UserCreateDTO createUser(UserCreateDTO userCreateDTO, Long idLogin) {

        boolean emailExist = userRepository.existsByEmail(userCreateDTO.getEmail());
        User user = userRepository.findById(idLogin).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, idLogin)));
        if (emailExist) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, user.getEmail()));
        }

        User createUser = userMapper.userCreateDTOToUser(userCreateDTO);

        Set<Role> loginUserRoles = user.getRoles();
        RoleType loginUser = loginUserRoles.stream().findFirst().get().getName();

        if (loginUser.equals(RoleType.ROLE_STAFF)) {
            createUser.setRoles(convertRoles(userCreateDTO.getRoleName()));
        }
        if (loginUser.equals(RoleType.ROLE_ADMIN)) {
            createUser.setRoles(convertRoles(userCreateDTO.getRoleName()));
        }

        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        userCreateDTO.setPassword(encodedPassword);
        createUser.setPassword(encodedPassword);
        userRepository.save(createUser);
        userCreateDTO.setId(createUser.getId());
        return userCreateDTO;
    }

    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, request.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_MEMBER.name())));


        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setPassword(encodedPassword);
        user.setBirthDate(request.getBirthDate());
        user.setCreateDate(request.getCreateDate());
        user.setPhone(request.getPhone());
        user.setResetPasswordCode(request.getResetPasswordCode());
        user.setRoles(roles);

        userRepository.save(user);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;

    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUserPage(Optional<String> query, Pageable pageable) {

        Page<UserDTO> dtoPage;

        dtoPage = userRepository.findUsersQueryOptionalSearchWithPage(query, pageable);
        return dtoPage;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.map(users);
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        return userMapper.userToUserDTO(user);
    }

    public UserDTO delUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(String.format(ErrorMessage.CANT_PROCESS__WITH_BUILT_IN_TRUE_USER));
        }
        if (!user.getIsActive()) {
            throw new BadRequestException("User is not already active");
        }

        List<Loan> loans = loanRepository.getUserLoans(id);

        for (Loan each : loans) {
            if (!each.equals(null)) {
                throw new BadRequestException(ErrorMessage.NOT_DELETE_USER_HAS_LOANS);
            }
        }
        user.setIsActive(false);
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    public UserDTO updateUserByAdminOrStaff(Long id, AdminUpdateUserRequest adminUpdateUserRequest) {
        boolean emailExist = userRepository.existsByEmail(adminUpdateUserRequest.getEmail());

        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.CANT_PROCESS__WITH_BUILT_IN_TRUE_USER);
        }

        if (emailExist && !adminUpdateUserRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,user.getEmail()));
        }

        if (adminUpdateUserRequest.getPassword() == null) {
            adminUpdateUserRequest.setPassword(user.getPassword());
        } else {
            String encodedPassword = passwordEncoder.encode(adminUpdateUserRequest.getPassword());
            adminUpdateUserRequest.setPassword(encodedPassword);
        }

        User updateUser = userMapper.adminUpdateUserRequest(adminUpdateUserRequest);
         updateUser.setRoles(convertRoles(adminUpdateUserRequest.getRoles()));
         updateUser.setId(user.getId());

        userRepository.save(updateUser);

        return userMapper.userToUserDTO(updateUser);



      /*userRepository.update(id, adminUpdateUserRequest.getFirstName(), adminUpdateUserRequest.getLastName(), adminUpdateUserRequest.getPhone(), adminUpdateUserRequest.getEmail(),
               adminUpdateUserRequest.getAddress(), adminUpdateUserRequest.getBirthDate(),adminUpdateUserRequest.getIsActive(),adminUpdateUserRequest.getBuiltIn(), adminUpdateUserRequest.getResetPasswordCode(),
               adminUpdateUserRequest.getCreateDate(),adminUpdateUserRequest.getScore());*/

    }


    @Transactional
    public UserDTO updateUser(Long id, UserUpdateRequest request) {
        boolean existEmail = userRepository.existsByEmail(request.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.CANT_PROCESS__WITH_BUILT_IN_TRUE_USER);
        }

        if (existEmail && !request.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, user.getEmail()));
        }

        User updateUser = userMapper.updateUserBySelf(request);
        updateUser.setCreateDate(user.getCreateDate());
        updateUser.setBuiltIn(user.getBuiltIn());
        updateUser.setScore(user.getScore());
        updateUser.setPassword(user.getPassword());
        updateUser.setId(user.getId());
        updateUser.setRoles(user.getRoles());

        userRepository.save(updateUser);
        return userMapper.userToUserDTO(updateUser);
    }

    public void updatePassword(Long id, UpdatePasswordRequest passwordRequest) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.CANT_PROCESS__WITH_BUILT_IN_TRUE_USER);
        }
        if (!user.getIsActive()) {
            throw new BadRequestException("User doesn't active");
        }
        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_DOESNT_MATCH);
        }

        String encodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public List<LoanResponse> getUserLoans(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        List<LoanResponse> authUserLoans = loanRepository.getAuthUserLoans(id);

        if (authUserLoans.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.LOAN_NOT_FOUND_MESSAGE, id));
        }

        return authUserLoans;
    }

    public Set<Role> setRoles(Long roleId) {
        Set<Role> role = new HashSet<>();
        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE));
        Role staffRole = roleRepository.findByName(RoleType.ROLE_STAFF).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE));
        Role memberRole = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE));
        if (roleId == 1) {
            role.add(adminRole);
        } else if (roleId == 2) {
            role.add(staffRole);
        } else {
            role.add(memberRole);
        }
        return role;
    }

    private Set<Role> convertRoles(String userStrRoles) {

        Set<Role> roles = new HashSet<>();

        if (userStrRoles == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_MEMBER.name())));
            roles.add(userRole);
        } else {

                switch (userStrRoles) {
                    case "Administrator":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow(() ->
                                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_ADMIN.name())));
                        roles.add(adminRole);
                        break;
                    case "Staff":
                        Role staffRole = roleRepository.findByName(RoleType.ROLE_STAFF).orElseThrow(() ->
                                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_STAFF.name())));
                        roles.add(staffRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() ->
                                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_MEMBER.name())));
                        roles.add(userRole);
                }

        }
        return roles;

    }
}
