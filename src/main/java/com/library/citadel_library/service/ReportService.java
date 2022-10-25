package com.library.citadel_library.service;

import com.library.citadel_library.dto.*;
import com.library.citadel_library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReportService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;
    CategoryRepository categoryRepository;
    LoanRepository loanRepository;

    public ReportStatisticDTO getAllStatistic() {

        int books = bookRepository.findAll().size();
        int authors = authorRepository.findAll().size();
        int publishers = publisherRepository.findAll().size();
        int categories = categoryRepository.findAll().size();
        int loans = loanRepository.findAll().size();

        ReportStatisticDTO statistics = new ReportStatisticDTO();

        statistics.setBooksNumber(books);
        statistics.setAuthorNumber(authors);
        statistics.setPublisherNumber(publishers);
        statistics.setCategoryNumber(categories);
        statistics.setLoansNumber(loans);

        return statistics;
    }

    @Transactional
    public Page<ReportMostPopularBookDTO> getMostPopularBooksWithPage(Pageable pageable) {

       Page<ReportMostPopularBookDTO> mostPopulars = loanRepository.mostPopulars(pageable);

        return mostPopulars;
    }

    public Page<BookDTO> getExpiredBooksWithPage(Pageable pageable, LocalDateTime time) {

        Page<BookDTO> bookDTOs = loanRepository.expiredBooks(pageable,time);

        return bookDTOs;
    }

    public Page<BookDTO> getUnreturnedBooksWithPage(Pageable pageable, LocalDateTime time) {

        Page<BookDTO> bookDTOs = loanRepository.unreturned(pageable,time);

        return bookDTOs;
    }


    public Page<ReportMostBorrowersDTO> getMostBorrowersWithPage(Pageable pageable) {

        Page<ReportMostBorrowersDTO> mostBorrowers = loanRepository.mostBorrowers(pageable);

        return mostBorrowers;
    }



    /*

    unreturned için alternatif çözüm
     List<Loan> loans = loanRepository.findAll();
        List<Loan> unreturnedList = new ArrayList<>();
        List<BookDTO> unreturnedBookList = new ArrayList<>();

        for (Loan each: loans) {
            if (each.getExpireDate().isBefore(LocalDateTime.now()) && each.getReturnDate() == null){
                unreturnedList.add(each);
            }
        }

        if(unreturnedList.isEmpty()){
            throw new BadRequestException("tüm kitaplar geri verilmiştir.");
        }

        for (Loan each: unreturnedList) {
            Book book = each.getBook();
            BookDTO bookDTO = bookMapper.bookToBookDTO(book);
            bookDTO.setCategory_id(book.getCategory().getId());
            bookDTO.setAuthor_id(book.getAuthor().getId());
            bookDTO.setPublisher_id(book.getPublisher().getId());

            unreturnedBookList.add(bookDTO);
        }

        return unreturnedBookList;
     */
}
