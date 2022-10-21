package com.library.citadel_library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 17, nullable = false)
    private String isbn;

    private Integer pageCount;

    @Column(nullable = false)
    private Integer publishDate;

    @Column(nullable = false)
    private Boolean loanable = true;

    @Column(length = 6, nullable = false)
    private String shelfCode;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Boolean featured = false;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ImageFile image;

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

    public Book(String name, String isbn, Integer pageCount, Integer publishDate, Boolean loanable, String shelfCode, Boolean active, Boolean featured, LocalDateTime createDate, Boolean builtIn, Author author, Category category, Publisher publisher) {
        this.name = name;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
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
