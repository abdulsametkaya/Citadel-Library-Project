package com.library.citadel_library.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class LoanUpdateRequest {

    @NotNull(message = "Please provide a expireDate")
    private LocalDateTime expireDate;

    @NotNull(message = "Please provide a returnDate")
    private LocalDateTime returnDate;

    @NotNull(message = "Please provide a notes")
    private String notes;
}
