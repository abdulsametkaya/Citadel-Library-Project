package com.library.citadel_library.dto;

import com.library.citadel_library.domain.Role;
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
    private Date birthDate;
    private String email;
    private LocalDateTime createDate;
    private String resetPasswordCode;
    private Boolean builtIn = false;
    private Set<String> roles;

    public  void setRoles(Set<Role> roles){
        Set<String> rolesStr= new HashSet<>();

        roles.forEach(r->  {
            if (r.getName().equals(RoleType.ROLE_ADMIN)){ rolesStr.add("Administrator");}
            else if (r.getName().equals(RoleType.ROLE_STAFF)) {rolesStr.add("Staff");}
            else  {rolesStr.add("Member");}
        });
        this.roles=rolesStr;
    }

}
