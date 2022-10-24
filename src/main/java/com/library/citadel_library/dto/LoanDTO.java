package com.library.citadel_library.dto;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.User;
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
    private LocalDateTime loanDate;

    @NotNull(message="Please provide expire date")
    private LocalDateTime expireDate;

    @NotNull(message="Please provide return")
    private LocalDateTime returnDate;

    @Size(min = 10,max = 200, message = "Message must be between ${min} and ${max} chars long")
    private String notes;


    private Long userId;


    private Long bookId;

}
