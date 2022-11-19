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

    private String authorName;

    private String image_id;
    public ReportMostPopularBookDTO(Long id, String name, String isbn, Integer pageCount, Long amount,String authorName,String image_id) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.amount = amount;
        this.authorName = authorName;
        this.image_id = image_id;
    }
}
