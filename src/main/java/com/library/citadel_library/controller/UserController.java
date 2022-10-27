package com.library.citadel_library.controller;



import com.library.citadel_library.dto.UserCreateDTO;
import com.library.citadel_library.dto.UserDTO;
import com.library.citadel_library.dto.requests.AdminUpdateUserRequest;
import com.library.citadel_library.dto.requests.UpdatePasswordRequest;
import com.library.citadel_library.dto.requests.UserUpdateRequest;
import com.library.citadel_library.dto.response.CLResponse;
import com.library.citadel_library.dto.response.UserLoansResponse;
import com.library.citadel_library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    // http://localhost:8080/users/add Admin veya employee create user
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PostMapping("/add")
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO,HttpServletRequest request){
       Long idLogin = (Long)request.getAttribute("id");
        UserCreateDTO user = userService.createUser(userCreateDTO,idLogin);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users= userService.getAllUsers();

        return ResponseEntity.ok(users);
    }


   // http://localhost:8080/users  It will return the authenticatedauthenticated user object
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<UserDTO> getUserById(HttpServletRequest request){
      Long id = (Long) request.getAttribute("id");
      UserDTO userDTO = userService.getUser(id);
      return ResponseEntity.ok(userDTO);
    }


     // http://localhost:8080/users/4  admin or staff can return user object with user id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserDTO> getUserByIdAdmin(@PathVariable Long id){
    UserDTO userDTO=userService.getUser(id);
    return ResponseEntity.ok(userDTO);
    }


   // http://localhost:8080/users/page?search=167-675-4443
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<UserDTO>> getAllUsersPage(@RequestParam(required = false,value = "search")Optional <String> query,
                                                         @RequestParam(required = false, value = "page",defaultValue = "0") int page,
                                                         @RequestParam(required = false, value = "size",defaultValue = "20") int size,
                                                         @RequestParam(required = false, value = "sort",defaultValue = "id") String prop,
                                                         @RequestParam(required = false, value = "direction",defaultValue = "DESC")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<UserDTO> userDTOPage = userService.getUserPage(query,pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    // http://localhost:8080/users/loans  -
    //It will return the authenticated user object it should return the corresponding book object in response
    @GetMapping("/loans")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<Page<UserLoansResponse>> getUserLoansPage(@RequestParam(required = false, value = "page",defaultValue = "0") int page,
                                                                    @RequestParam(required = false, value = "size",defaultValue = "20") int size,
                                                                    @RequestParam(required = false, value = "sort",defaultValue = "id") String prop,
                                                                    @RequestParam(required = false, value = "direction",defaultValue = "DESC")Sort.Direction direction,
                                                                    HttpServletRequest request){
        Long id =(Long)request.getAttribute("id");
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<UserLoansResponse> userLoansPage = userService.getUserLoans(id,pageable);
        return ResponseEntity.ok(userLoansPage);


    }
    // http://localhost:8080/users/delete/43
    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable  Long id){
       UserDTO userDTO = userService.delUser(id);

        return ResponseEntity.ok(userDTO);
    }

    // http://localhost:8080/users/2 -
    //It will return the updated user object admin can update any type of user,while an employee can update only member-type users.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserDTO>  updateUserByAdminOrStaff(HttpServletRequest request,@PathVariable Long id, @Valid @RequestBody AdminUpdateUserRequest adminUpdateUserRequest){
      Long idLogin = (Long) request.getAttribute("id");
       UserDTO userDTO =  userService.updateUserByAdminOrStaff(id,idLogin,adminUpdateUserRequest);

       return ResponseEntity.ok(userDTO);
    }


    @PatchMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<UserDTO> updateUser(HttpServletRequest request, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
      Long id = (Long) request.getAttribute("id");
      UserDTO userDTO = userService.updateUser(id,userUpdateRequest);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<CLResponse> updatePassword (HttpServletRequest request, @RequestBody UpdatePasswordRequest passwordRequest){
      Long id = (Long) request.getAttribute("id");
      userService.updatePassword(id,passwordRequest);


      CLResponse response = new CLResponse();
      response.setMessage("Password has changed");
      response.setSuccess(true);

      return ResponseEntity.ok(response);
    }
}
