package com.library.citadel_library.dto.response;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponse {
    private Long id;
    private Long userId;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;

    private Long bookId;

    private String name;

    private String isbn;

    private Integer pageCount;

    private Integer publishDate;

    private Boolean loanable = true;

    private String shelfCode;

    private String author;

    private String image_id;

    private String category;

    private String publisher;
    private User user;

    private Long bookAmount;
    public LoanResponse(Loan loan,User user, Book book){
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.userId = loan.getUser().getId();
        this.bookId = loan.getBook().getId();
        this.name = book.getName();
        this.isbn = book.getIsbn();
        this.pageCount = book.getPageCount();
        this.publishDate = book.getPublishDate();
        this.loanable = book.getLoanable();
        this.shelfCode = book.getShelfCode();
        this.author = book.getAuthor().getName();
        this.image_id = book.getImage().getId();
        this.category = book.getCategory().getName();
        this.publisher = book.getPublisher().getName();
        this.user = user;

    }

}
