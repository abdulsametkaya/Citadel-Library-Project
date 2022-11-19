package com.library.citadel_library.dto.response;

import com.library.citadel_library.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanAmountCategoryResponse {

    private String category;
    private Long bookAmount;


    public LoanAmountCategoryResponse(Long bookAmount, Book book){
      this.bookAmount = bookAmount;
        this.category = book.getCategory().getName();


    }
}
