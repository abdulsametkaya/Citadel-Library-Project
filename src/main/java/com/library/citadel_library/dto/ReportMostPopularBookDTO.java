package com.library.citadel_library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMostPopularBookDTO {
    private Long id;
    private String name;
    private String isbn;
    private Integer pageCount;
    private Long amount;
    public ReportMostPopularBookDTO(Long id, String name, String isbn, Integer pageCount, Long amount) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.amount = amount;
    }
}
