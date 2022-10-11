package com.library.citadel_library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_publisher")
public class Publisher {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Please provide publisher name")
    @Size(min=2, max=50,message="Your name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 50,nullable = false)
    private String name;

    @NotNull(message="Please provide select builtIn")
    @Column(nullable = false)
    private Boolean builtIn = false;

    public Publisher(String name, Boolean builtIn) {
        this.name = name;
        this.builtIn = builtIn;
    }
}
