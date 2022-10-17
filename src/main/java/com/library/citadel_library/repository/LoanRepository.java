package com.library.citadel_library.repository;


import com.library.domain.Loan;
import com.library.dto.response.LoanResponse;
import com.library.dto.response.LoanResponseBook;
import com.library.dto.response.LoanResponseBookUser;
import com.library.dto.response.UserLoansResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {


    @Query("SELECT u FROM Loan u WHERE u.user.id = ?1")
    List<Loan> expireDate(Long id);

  @Query("SELECT new com.library.dto.response.LoanResponse(u) FROM Loan u WHERE u.user.id = ?1")
  Page<LoanResponse> getAutUserLoan(Long id, Pageable pageable);

  @Query("SELECT new com.library.dto.response.LoanResponse(u) FROM Loan u WHERE u.user.id = ?1 and u.id = ?2")
  LoanResponse getAutUserLoanId(Long idLogin, Long id);

  @Query("SELECT new com.library.dto.response.LoanResponseBook(u) FROM Loan u WHERE u.book.id = ?1")
  Page<LoanResponseBook> getSpecifiedBookLoan(Long id, Pageable pageable);

  @Query("SELECT new com.library.dto.response.LoanResponseBookUser(u) FROM Loan u WHERE u.id = ?1")
  LoanResponseBookUser getAnyUserLoanByEmployeAnyAdmin(Long id);

    @Query("SELECT new com.library.dto.response.UserLoansResponse(u) FROM Loan u WHERE u.user.id = ?1")
    Page<UserLoansResponse> getAuthUserLoans(Long id,Pageable pageable);
}
