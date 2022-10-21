package com.library.citadel_library.dto.mapper;

import com.library.citadel_library.domain.User;
import com.library.citadel_library.dto.UserCreateDTO;
import com.library.citadel_library.dto.UserDTO;
import com.library.citadel_library.dto.requests.AdminUpdateUserRequest;
import com.library.citadel_library.dto.requests.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    UserCreateDTO userToUserCreateDTO(User user);

    User userCreateDTOToUser(UserCreateDTO userCreateDTO);

    List<UserDTO> map(List<User>user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User adminUpdateUserRequest(AdminUpdateUserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User updateUserBySelf(UserUpdateRequest request);



}
