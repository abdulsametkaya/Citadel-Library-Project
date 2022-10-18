package com.library.citadel_library.dto;

import com.library.citadel_library.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Long id;

    @NotNull(message="Please provide book name")
    @Size(min=2, max=80,message="Book name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    @NotNull(message="Please provide isbn name")
    @Size(min=17, max=17,message="Isbn '${validatedValue}' must be between {min} and {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",message = "Please provide valid isbn")
    private String isbn;

    private Integer pageCount;

    @NotNull(message="Please provide publish date")
    private Integer publishDate;

    private File image;

    private Boolean loanable = true;

    @NotNull(message="Please provide shelf code ")
    @Size(min=6, max=6,message="ShelfCode '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$",message = "Please provide valid shelf code")
    private String shelfCode;

    private Boolean active = true;

    private Boolean featured = false;

    @NotNull(message="Please provide create date")
    private LocalDateTime createDate = LocalDateTime.now();

    private Boolean builtIn = false;

    @NotNull(message="Please provide create author")
    private Long author_id;

    @NotNull(message="Please provide create category")
    private Long category_id;

    @NotNull(message="Please provide create publisher")
    private Long publisher_id;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.isbn = book.getIsbn();
        this.pageCount = book.getPageCount();
        this.publishDate = book.getPublishDate();
        this.image = book.getImage();
        this.loanable = book.getLoanable();
        this.shelfCode = book.getShelfCode();
        this.active = book.getActive();
        this.featured = book.getFeatured();
        this.createDate = book.getCreateDate();
        this.builtIn = book.getBuiltIn();
        this.author_id = book.getAuthor().getId();
        this.category_id = book.getCategory().getId();
        this.publisher_id = book.getPublisher().getId();
    }
}
