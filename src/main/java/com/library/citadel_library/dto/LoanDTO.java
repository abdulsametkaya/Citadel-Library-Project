package com.library.citadel_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long id;
    @NotNull(message="Please provide load date")
    private LocalDateTime loanDate = LocalDateTime.now();

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;
    @Size(min = 10,max = 200, message = "Message must be between ${min} and ${max} chars long")
    private String notes;

    @NotNull(message="Please provide UserId")
    private Long userId;

    @NotNull(message="Please provide BookId")
    private Long bookId;


}
