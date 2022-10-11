package com.library.citadel_library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Please provide first name")
    @Size(min=2, max=30,message="Your first name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 30,nullable = false)
    private String firstName;

    @NotNull(message="Please provide last name")
    @Size(min=2, max=30,message="Your last name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 30,nullable = false)
    private String lastName;

    // TODO score ile ilgili detaylar sorulacak
    @NotNull(message="Please provide score")
    @Size(min=-2, max=2,message="Score '${validatedValue}' must be between {min} and {max} chars long")
    @Column(nullable = false)
    private Integer score = 0;

    @NotNull(message="Please provide address")
    @Size(min=10, max=100,message="Address '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 100,nullable = false)
    private String address;


    @NotNull(message="Please provide phone number")
    @Size(min=12, max=12,message="Phone number '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",message = "Please provide valid phone number")
    @Column(nullable = false)
    private String phone;

    private Date birthDate;

    @Email(message = "Please provide valid email")
    @NotNull(message="Please provide email")
    @Size(min=10, max=80,message="Email '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 80,nullable = false)
    private String email;

    @JsonIgnore
    @NotNull(message="Please provide password")
    @Column(nullable = false)
    private String password;

    @NotNull(message="Please provide createDate")
    @Column(nullable = false)
    private LocalDateTime createDate;

    private String resetPasswordCode;

    @NotNull(message="Please provide user builtIn")
    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, Integer score, String address, String phone, Date birthDate, String email, String password, LocalDateTime createDate, String resetPasswordCode, Boolean builtIn, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.resetPasswordCode = resetPasswordCode;
        this.builtIn = builtIn;
        this.roles = roles;
    }
}
