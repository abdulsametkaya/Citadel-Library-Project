package com.library.citadel_library.dto.mapper;

import com.library.citadel_library.dto.LoanDTO;
import com.library.citadel_library.domain.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface LoanMapper {

    LoanDTO loanToLoanDTO(Loan loan);
    Loan loanDTOToLoan(LoanDTO loanDTO);

}
