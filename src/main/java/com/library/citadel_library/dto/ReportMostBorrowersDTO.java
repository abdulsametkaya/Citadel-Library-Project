package com.library.citadel_library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMostBorrowersDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer score;
    private String phone;
    private String email;
    private Long amount;

    public ReportMostBorrowersDTO(Long id, String firstName, String lastName, Integer score, String phone, String email, Long amount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        this.phone = phone;
        this.email = email;
        this.amount = amount;
    }
}
