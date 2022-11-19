package com.library.citadel_library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.citadel_library.domain.Role;
import com.library.citadel_library.domain.User;
import com.library.citadel_library.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer score;
    private String address;
    private String phone;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy",timezone = "Turkey")
    private Date birthDate;
    private String email;
    private LocalDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String resetPasswordCode;

    private Boolean isActive = true;
    private Boolean builtIn = false;
    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> rolesStr = new HashSet<>();

        roles.forEach(r -> {
            if (r.getName().equals(RoleType.ROLE_ADMIN)) {
                rolesStr.add("Administrator");
            } else if (r.getName().equals(RoleType.ROLE_STAFF)) {
                rolesStr.add("Staff");
            } else {
                rolesStr.add("Member");
            }
        });
        this.roles = rolesStr;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.score = user.getScore();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.createDate = user.getCreateDate();
        this.resetPasswordCode = user.getResetPasswordCode();
        this.isActive = user.getIsActive();
    }
}
