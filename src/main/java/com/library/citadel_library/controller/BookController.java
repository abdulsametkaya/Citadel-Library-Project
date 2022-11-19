package com.library.citadel_library.controller;

import com.library.citadel_library.dto.BookDTO;
import com.library.citadel_library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {

    BookService bookService;

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDTO> getOneBook(@PathVariable Long id){
        BookDTO book = bookService.getOneBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BookDTO>> getAllWithPage(@RequestParam("name") Optional<String> p,
                                                        @RequestParam("cat") Optional<Long> categortyId,
                                                        @RequestParam("author") Optional<Long> authorId,
                                                        @RequestParam("publisher") Optional<Long> publisherId,
                                                        @RequestParam( required = false,value ="page", defaultValue = "0") int page,
                                                        @RequestParam( required = false,value ="size", defaultValue = "10") int size,
                                                        @RequestParam( required = false,value ="sort", defaultValue = "name") String prop,
                                                        @RequestParam( required = false,value ="type", defaultValue = "DESC") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<BookDTO> bookPage = bookService.findAllWithPage(p, categortyId, authorId, publisherId,pageable);

        return new ResponseEntity<>(bookPage,HttpStatus.OK);
    }

    @GetMapping("/allbooks")
    public ResponseEntity<List<BookDTO>> getAll(){

        List<BookDTO> books = bookService.findAll();

        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/bookspages")
    public ResponseEntity<Page<BookDTO>>  getAllBooksWithPage(@RequestParam(required = false,value = "page", defaultValue = "0") int page,
                                                      @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                      @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                      @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<BookDTO> booksPage = bookService.getAllBooksWithPage(pageable);
        return new ResponseEntity<>(booksPage, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDto){

        BookDTO book = bookService.createBook(bookDto);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){

       BookDTO book = bookService.updateBookById(id,bookDTO);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/delete/{id}")
    public ResponseEntity<BookDTO> deleteOneBook(@PathVariable Long id){

        BookDTO book = bookService.deleteOneBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);

    }

}
