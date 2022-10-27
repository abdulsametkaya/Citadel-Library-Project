package com.library.citadel_library.controller;

import com.library.citadel_library.dto.BookDTO;
import com.library.citadel_library.dto.ReportMostBorrowersDTO;
import com.library.citadel_library.dto.ReportMostPopularBookDTO;
import com.library.citadel_library.dto.ReportStatisticDTO;
import com.library.citadel_library.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {
    private ReportService reportService;

    // Tüm istatistiki Genel Verileri Getirir
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/all")
    public ResponseEntity<ReportStatisticDTO> getAllStatistic(){
        ReportStatisticDTO statistics =  reportService.getAllStatistic();

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    //Most popular Books
    @GetMapping()
    public ResponseEntity<Page<ReportMostPopularBookDTO>> getMostPopularBooksWithPage( @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                                       @RequestParam(required = false,value = "size", defaultValue = "20") int size
                                                                                       ){
        Pageable pageable = PageRequest.of(page,size);
        Page<ReportMostPopularBookDTO> mostPopularBook = reportService.getMostPopularBooksWithPage(pageable);

        return ResponseEntity.ok(mostPopularBook);
    }

    @GetMapping("/expired-books")
    public ResponseEntity<Page<BookDTO>> getExpiredBooks(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                            @RequestParam(required = false,value = "size", defaultValue = "20") int size){

        Pageable pageable = PageRequest.of(page,size);
        Page<BookDTO> unreturned = reportService.getExpiredBooksWithPage(pageable,LocalDateTime.now());

        return ResponseEntity.ok(unreturned);
    }

    @GetMapping("/unreturned-books")
    public ResponseEntity<Page<BookDTO>> getUnreturnedBooks(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                         @RequestParam(required = false,value = "size", defaultValue = "20") int size){

        Pageable pageable = PageRequest.of(page,size);
        Page<BookDTO> unreturned = reportService.getUnreturnedBooksWithPage(pageable, LocalDateTime.now());

        return ResponseEntity.ok(unreturned);
    }


    @GetMapping("/most-borrowers")
    public ResponseEntity<Page<ReportMostBorrowersDTO>> getMostBorrowers(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(required = false,value = "size", defaultValue = "20") int size)
    {

        Pageable pageable = PageRequest.of(page,size);

        Page<ReportMostBorrowersDTO> mostBorrowers = reportService.getMostBorrowersWithPage(pageable);

        return ResponseEntity.ok(mostBorrowers);
    }

    }
