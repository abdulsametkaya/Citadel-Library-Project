package com.library.citadel_library.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    @NotNull(message="Please provide name")
    @Size(min=4, max=70,message="Your name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    private Boolean builtIn = false;
}
