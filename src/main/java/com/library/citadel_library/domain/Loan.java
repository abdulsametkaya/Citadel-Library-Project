package com.library.citadel_library.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message="Please provide load date")
    @Column(nullable = false)
    private LocalDateTime loanDate;

    @NotNull(message="Please provide expire date")
    @Column(nullable = false)
    private LocalDateTime expireDate;


    @Column
    private LocalDateTime returnDate;

    @Column(length = 300)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    public Loan(LocalDateTime loanDate, LocalDateTime expireDate, LocalDateTime returnDate, String notes, User user, Book book) {
        this.loanDate = loanDate;
        this.expireDate = expireDate;
        this.returnDate = returnDate;
        this.notes = notes;
        this.user = user;
        this.book = book;
    }
}
