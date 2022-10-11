package com.library.citadel_library.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Please provide book name")
    @Size(min=2, max=80,message="Book name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 80,nullable = false)
    private String name;

    @NotNull(message="Please provide isbn name")
    @Size(min=17, max=17,message="Isbn '${validatedValue}' must be between {min} and {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",message = "Please provide valid isbn")
    @Column(length = 17,nullable = false)
    private String isbn;

    private Integer pageCount;

    @NotNull(message="Please provide publish date")
    @Column(nullable = false)
    private Integer publishDate;

    private File image;

    @NotNull(message="Please provide loanable")
    @Column(nullable = false)
    private Boolean loanable = true;

    @NotNull(message="Please provide shelf code ")
    @Size(min=6, max=6,message="ShelfCode '${validatedValue}' must be {max} chars long")
    @Column(length = 6,nullable = false)
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$",message = "Please provide valid shelf code")
    private String shelfCode;

    @NotNull(message="Please provide active")
    @Column(nullable = false)
    private Boolean active = true;

    @NotNull(message="Please provide featured")
    @Column(nullable = false)
    private Boolean featured = false;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publisher publisher;


    public Book(String name, String isbn, Integer pageCount, Integer publishDate, File image, Boolean loanable, String shelfCode, Boolean active, Boolean featured, LocalDateTime createDate, Boolean builtIn, Author author, Category category, Publisher publisher) {
        this.name = name;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
        this.image = image;
        this.loanable = loanable;
        this.shelfCode = shelfCode;
        this.active = active;
        this.featured = featured;
        this.createDate = createDate;
        this.builtIn = builtIn;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
    }
}
