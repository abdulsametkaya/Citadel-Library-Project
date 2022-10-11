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
@Table(name="tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Please provide category name")
    @Size(min=2, max=80,message="Category name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 80,nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean builtIn = false;

    //TODO Not null, default: one more than the largest number in sequence fields detaylar sorulacak
    @NotNull(message="Please provide sequence")
    @Column(nullable = false)
    private Integer sequence;


    public Category(String name, Boolean builtIn, Integer sequence) {
        this.name = name;
        this.builtIn = builtIn;
        this.sequence = sequence;
    }
}
