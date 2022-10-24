package com.library.citadel_library.dto.response;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponseBookUser {


    private Long id;

    private Long userId;

    private Long bookId;

    private LocalDateTime loanDate;

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;

    private String notes;

    private User user;

    private Book book;


    public LoanResponseBookUser(Loan loan) {
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.userId = loan.getUser().getId();
        this.bookId = loan.getBook().getId();
        this.notes = loan.getNotes();
        this.user = loan.getUser();
        this.book = loan.getBook();
    }
}
