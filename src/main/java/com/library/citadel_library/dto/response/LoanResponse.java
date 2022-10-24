package com.library.citadel_library.dto.response;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.Loan;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponse {
    private Long id;

    private Long userId;

    private Long bookId;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
    private Book book;
    public LoanResponse(Loan loan) {
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.userId = loan.getUser().getId();
        this.bookId = loan.getBook().getId();
        this.book = loan.getBook();
    }
}
