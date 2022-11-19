package com.library.citadel_library.repository;


import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.dto.BookDTO;
import com.library.citadel_library.dto.ReportMostBorrowersDTO;
import com.library.citadel_library.dto.ReportMostPopularBookDTO;
import com.library.citadel_library.dto.response.*;
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

  @Query("SELECT new com.library.citadel_library.dto.response.LoanResponse(u,u.user,u.book) FROM Loan u WHERE u.user.id = ?1")
  Page<LoanResponse> getAutUserLoan(Long id, Pageable pageable);

  @Query("SELECT new com.library.citadel_library.dto.response.LoanResponse(u,u.user, u.book) FROM Loan u WHERE u.user.id = ?1 and u.id = ?2")
  LoanResponse getAutUserLoanId(Long idLogin, Long id);

  @Query("SELECT new com.library.citadel_library.dto.response.LoanResponseBook(u) FROM Loan u WHERE u.book.id = ?1")
  Page<LoanResponseBook> getSpecifiedBookLoan(Long id, Pageable pageable);

  @Query("SELECT new com.library.citadel_library.dto.response.LoanResponse(u,u.user, u.book) FROM Loan u WHERE u.id = ?1")
  LoanResponse getAnyUserLoanByEmployeAnyAdmin(Long id);


    @Query("SELECT new com.library.citadel_library.dto.response.LoanResponse(u,u.user, u.book) FROM Loan u WHERE (u.user.id = ?1 and u.returnDate is null)")
    List<LoanResponse> getAuthUserLoans(Long id);


    @Query("SELECT new com.library.citadel_library.dto.ReportMostPopularBookDTO(max(l.book.id), max(l.book.name), max(l.book.isbn), " +
            "max(l.book.pageCount), count(l.book.id),max(l.book.author.name),max(l.book.image.id)) from Loan l  group by l.book.id order by count(l.book.id) desc ")
  Page<ReportMostPopularBookDTO> mostPopulars(Pageable pageable);

    @Query("SELECT new com.library.citadel_library.dto.BookDTO(l.book) from Loan l where (l.returnDate is null)")
    Page<BookDTO> unreturned(Pageable pageable);

  @Query("SELECT new com.library.citadel_library.dto.BookDTO(l.book) from Loan l where l.expireDate < :date")
  Page<BookDTO> expiredBooks(Pageable pageable, LocalDateTime date);

    @Query("SELECT new com.library.citadel_library.dto.ReportMostBorrowersDTO(max(l.user.id), max(l.user.firstName), max(l.user.lastName), " +
            "max(l.user.score), max(l.user.phone), max(l.user.email),count(l.user.id)) from Loan l  group by l.user.id order by count(l.user.id) desc ")
  Page<ReportMostBorrowersDTO> mostBorrowers(Pageable pageable);

  @Query("SELECT count(u) FROM Loan u WHERE u.returnDate is null")
  Integer  getUnReturnedBooks();

  @Query("SELECT count(u) FROM Loan u WHERE (u.returnDate is null and u.expireDate<:date)")
  int getExpiredBooks(LocalDateTime date);


  @Query("SELECT new com.library.citadel_library.dto.response.LoanAmountCategoryResponse(count(l.book.category.id), max(l.book)) from Loan l WHERE l.user.id = ?1  group by l.book.category.id order by count(l.book.category.id) desc")
  Page<LoanAmountCategoryResponse> userLoansCountWithCategoryName(Long id,Pageable pageable);
}
