package com.library.citadel_library.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class LoanUpdateRequest {


    private LocalDateTime returnDate=LocalDateTime.now();


}
