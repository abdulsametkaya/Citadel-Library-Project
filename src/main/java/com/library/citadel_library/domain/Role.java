package com.library.citadel_library.domain;

import com.library.citadel_library.domain.enums.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Please provide role")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;

    public Role(RoleType name) {
        this.name = name;
    }
}
