package com.library.citadel_library.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateUserRequest {

    @NotNull(message="Please provide last name")
    @Size(min=2, max=30,message="Your last name '${validatedValue}' must be between {min} and {max} chars long")
    private String firstName;
    @NotNull(message="Please provide last name")
    @Size(min=2, max=30,message="Your last name '${validatedValue}' must be between {min} and {max} chars long")
    private String lastName;

    @Size(min = 4, max = 20,message="Please Provide Correct Size for Password")
    private String password;

    @NotNull(message="Please provide phone number")
    @Size(min=12, max=12,message="Phone number '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",message = "Please provide valid phone number")
    private String phone;

    @Email(message = "Please provide valid email")
    @NotNull(message="Please provide email")
    @Size(min=10, max=80,message="Email '${validatedValue}' must be between {min} and {max} chars long")
    private String email;
    private Integer score = 0;
    @NotNull(message="Please provide address")
    @Size(min=10, max=100,message="Address '${validatedValue}' must be between {min} and {max} chars long")
    private String address;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy",timezone = "Turkey")
    private Date birthDate;

    private LocalDateTime createDate;
    private String resetPasswordCode;
    @NotNull
    private Boolean isActive=true;

    private Boolean builtIn;

    private String roles;
}
