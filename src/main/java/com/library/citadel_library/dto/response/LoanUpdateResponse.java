package com.library.citadel_library.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanUpdateResponse {
    private Long id;
    private Long userId;
    private Long bookId;
    private String notes;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;


}
