package com.library.citadel_library.repository;


import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.dto.BookDTO;
import com.library.citadel_library.dto.ReportMostBorrowersDTO;
import com.library.citadel_library.dto.ReportMostPopularBookDTO;
import com.library.citadel_library.dto.response.LoanResponse;
import com.library.citadel_library.dto.response.LoanResponseBook;
import com.library.citadel_library.dto.response.LoanResponseBookUser;
import com.library.citadel_library.dto.response.UserLoansResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {


    @Query("SELECT u FROM Loan u WHERE u.user.id = ?1")
    List<Loan> getUserLoans(Long id);

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

    @Query("SELECT new com.library.dto.ReportMostPopularBookDTO(max(l.book.id), max(l.book.name), max(l.book.isbn), " +
            "max(l.book.pageCount), count(l.book.id)) from Loan l  group by l.book.id order by count(l.book.id) desc ")
  Page<ReportMostPopularBookDTO> mostPopulars(Pageable pageable);

    @Query("SELECT new com.library.dto.BookDTO(l.book) from Loan l where (l.returnDate is null and l.expireDate < :date)")
    Page<BookDTO> unreturned(Pageable pageable, LocalDateTime date);

  @Query("SELECT new com.library.dto.BookDTO(l.book) from Loan l where l.expireDate < :date")
  Page<BookDTO> expiredBooks(Pageable pageable, LocalDateTime date);

    @Query("SELECT new com.library.dto.ReportMostBorrowersDTO(max(l.user.id), max(l.user.firstName), max(l.user.lastName), " +
            "max(l.user.score), max(l.user.phone), max(l.user.email),count(l.user.id)) from Loan l  group by l.user.id order by count(l.user.id) desc ")
  Page<ReportMostBorrowersDTO> mostBorrowers(Pageable pageable);
}
