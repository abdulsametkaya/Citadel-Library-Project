package com.library.citadel_library.dto.mapper;

import com.library.DTO.LoanDTO;
import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface LoanMapper {

    LoanDTO loanToLoanDTO(Loan loan);
    Loan loanDTOToLoan(LoanDTO loanDTO);

}
