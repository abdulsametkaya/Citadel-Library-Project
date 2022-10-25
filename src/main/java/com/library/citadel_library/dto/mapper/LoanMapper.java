package com.library.citadel_library.dto.mapper;

import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.dto.LoanDTO;
import com.library.citadel_library.dto.response.LoanUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel="spring")
public interface LoanMapper {

    LoanUpdateResponse loanToLoanDTO(Loan loan);

    @Mapping(target = "expireDate", ignore = true)
    Loan loanDTOToLoan(LoanDTO loanDTO);

}
