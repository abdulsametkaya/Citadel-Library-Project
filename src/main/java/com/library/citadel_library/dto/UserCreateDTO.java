package com.library.citadel_library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserCreateDTO {

    private Long id;

    @NotNull(message = "Please provide first name")
    @Size(min = 2, max = 30, message = "Your first name '${validatedValue}' must be between {min} and {max} chars long")
    private String firstName;

    @NotNull(message = "Please provide last name")
    @Size(min = 2, max = 30, message = "Your last name '${validatedValue}' must be between {min} and {max} chars long")
    private String lastName;

    private Integer score = 0;

    @NotNull(message = "Please provide address")
    @Size(min = 10, max = 100, message = "Address '${validatedValue}' must be between {min} and {max} chars long")
    private String address;


    @NotNull(message = "Please provide phone number")
    @Size(min = 12, max = 12, message = "Phone number '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Please provide valid phone number")
    private String phone;

    private Date birthDate;

    @Email(message = "Please provide valid email")
    @NotNull(message = "Please provide email")
    @Size(min = 10, max = 80, message = "Email '${validatedValue}' must be between {min} and {max} chars long")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Please provide password")
    private String password;

    private LocalDateTime createDate = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String resetPasswordCode;
    @NotNull
    private Boolean isActive = true;
    private Boolean builtIn = false;
    private Long roleId;


}
