package com.library.citadel_library.service;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.Loan;
import com.library.citadel_library.domain.User;
import com.library.citadel_library.dto.LoanDTO;
import com.library.citadel_library.dto.mapper.LoanMapper;
import com.library.citadel_library.dto.response.*;
import com.library.citadel_library.exception.BadRequestException;
import com.library.citadel_library.exception.ResourceNotFoundException;
import com.library.citadel_library.exception.message.ErrorMessage;
import com.library.citadel_library.repository.BookRepository;
import com.library.citadel_library.repository.LoanRepository;
import com.library.citadel_library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class LoanService {

    LoanRepository loanRepository;
    LoanMapper loanMapper;

    BookRepository bookRepository;
    UserRepository userRepository;


    public LoanDTO createLoan(LoanDTO loanDTO){

      LocalDateTime ld = LocalDateTime.now();

        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, loanDTO.getBookId())));
      Boolean isLoanable =  book.getLoanable();
        if(!isLoanable){
            throw new BadRequestException("Book not available.");
        }

        User user= userRepository.findById(loanDTO.getUserId()).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, loanDTO.getUserId())));

        //Kullanıcının aldığı kitapların listesiniz alıyoruz.
         List<Loan> userLoans = loanRepository.getUserLoans(loanDTO.getUserId());

        //loan isteğinde bulunan kullanıcının aldığı kitaplardan expire tarihlerini alıyoruz calculateUserIsLoanable methodunda getirmediği kitap olup olmadığı kontrol ediliyor.
         calculateUserIsLoanable(userLoans,user.getScore());

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setNotes(loanDTO.getNotes());
        loan.setExpireDate(ld.plusDays(calculateDay(user.getScore())));
        loan.setLoanDate(ld);

       loanRepository.save(loan);

       loanDTO.setId(loan.getId());
       loanDTO.setNotes(loan.getNotes());
       loanDTO.setExpireDate(loan.getExpireDate());
       book.setLoanable(false);
       bookRepository.save(book);

        return loanDTO;
    }

    // kullanıcının score puanına göre kitabın kullanıcıda kalma süresini hesaplama methodu
    private int calculateDay(int score) {
        int day=0;

        if(score>=2){
            day = 20;
        }else if(score==1){
            day = 15;
        }else if(score==0){
            day= 10;
        }else if (score==-1){
            day = 6;
        }else if(score<=-2){
            day = 3;
        }
        return day;
    }

    // Kullanıcının aldığı kitabı getirmeme durumuna (expiredate) göre kitap alıp alamayacağını kontrol eden method
    public void calculateUserIsLoanable(List<Loan> userLoans, int score){
        LocalDateTime ld = LocalDateTime.now();
        int maxBookLoan = userHowMuchBookGet(score);
        int sayac = 0;
        for (Loan l:userLoans) {
            if(l.getReturnDate() == null){
                sayac++;
                Boolean expired =  l.getExpireDate().isBefore(ld);
                if(expired){
                    throw new BadRequestException("Your book has expired date, can not borrow book.");
                }else if(sayac>=maxBookLoan){
                    throw new BadRequestException("Reached Your Book Quota.");
                }
            }
        }
    }

    //Kullanıcının kaç kitap alabileceğini kontrol eden method
    public int userHowMuchBookGet(int score){
        int book = 0;

        if(score>=2){
            book = 5;
        }else if(score==1){
            book = 4;
        }else if(score==0){
            book= 3;
        }else if (score==-1){
            book = 2;
        }else if(score<=-2){
            book = 1;
        }

        return book;
    }


    @Transactional(readOnly = true)
    public Page<LoanResponse> getAuthenticatedUserLoans(Pageable pageable,Long idLogin) {
        User user= userRepository.findById(idLogin).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, idLogin)));

        Page<LoanResponse> authUserLoans = loanRepository.getAutUserLoan(idLogin,pageable);

        if(authUserLoans.isEmpty()) throw new BadRequestException("Kullanıcıya ait kayıt bulunamamıştır.");

        return authUserLoans;
    }

    public LoanResponse getAuthenticatedUserLoanWithId(Long idLogin, Long id) {
        User user= userRepository.findById(idLogin).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, idLogin)));
        LoanResponse authUserLoan = loanRepository.getAutUserLoanId(idLogin,id);

        if(authUserLoan == null) throw new BadRequestException("Kullanıcıya ait kayıt bulunamamıştır.");

        return authUserLoan;
    }



    public Page<LoanResponse> getLoansSpecifiedUserById(Pageable pageable, Long id) {
        User user= userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        Page<LoanResponse> authUserLoans = loanRepository.getAutUserLoan(id,pageable);
        if(authUserLoans.isEmpty()) throw new BadRequestException("Kullanıcıya ait kayıt bulunamamıştır.");

        return authUserLoans;
    }

    public Page<LoanResponseBook> getLoansSpecifiedBookById(Pageable pageable, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        Page<LoanResponseBook> authUserLoans = loanRepository.getSpecifiedBookLoan(id,pageable);
        if(authUserLoans.isEmpty()) throw new BadRequestException("İlgili kitaba ait kayıt bulunamamıştır.");

        return authUserLoans;
    }

    public LoanResponse getloanBookAndUser(Long id) {
        LoanResponse loan = loanRepository.getAnyUserLoanByEmployeAnyAdmin(id);

        if(loan == null) throw new BadRequestException("Kayıt bulunamamıştır");
        return loan;
    }



    public LoanUpdateResponse deleteLoan(Long id) {

        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.LOAN_NOT_FOUND_MESSAGE, id)));

        Book book = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        User user= userRepository.findById(loan.getUser().getId()).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        loan.setReturnDate(LocalDateTime.now());
        loanRepository.save(loan);

        book.setLoanable(true);
        bookRepository.save(book);

        if(loan.getExpireDate().isBefore(loan.getReturnDate())){
            user.setScore(user.getScore()-1);
        }else user.setScore(user.getScore()+1);

        userRepository.save(user);

        LoanUpdateResponse loanUpdateResponse = new LoanUpdateResponse();
        loanUpdateResponse.setId(loan.getId());
        loanUpdateResponse.setBookId(loan.getBook().getId());
        loanUpdateResponse.setExpireDate(loan.getExpireDate());
        loanUpdateResponse.setLoanDate(loan.getLoanDate());
        loanUpdateResponse.setUserId(loan.getUser().getId());
        loanUpdateResponse.setNotes(loan.getNotes());
        loanUpdateResponse.setReturnDate(loan.getReturnDate());

        return loanUpdateResponse;
    }

    public Page<LoanAmountCategoryResponse> getMostLoanBookAmountOfCategory(Long id,Pageable pageable) {
        User user= userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

        Page<LoanAmountCategoryResponse> loan = loanRepository.userLoansCountWithCategoryName(id,pageable);

        if(loan == null) throw new BadRequestException("Kayıt bulunamamıştır");
        return loan;

    }
}
