package com.library.citadel_library.controller;

import com.library.citadel_library.domain.Author;
import com.library.citadel_library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    /* Get All Author with Pageable */
    @GetMapping()
    public ResponseEntity<Page<Author>> getAllAuthors(@RequestParam(required = false,value = "page", defaultValue = "0") int page,
                                                            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                            @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                            @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Author> authorPage = authorService.getAllAuthorsWithPage(pageable);
        return new ResponseEntity<>(authorPage, HttpStatus.OK);
    }

    /* Get One Author with Author Id */
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Author author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author,HttpStatus.OK);
    }

    /* Create new Author */
    @PostMapping("/add")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author){
        authorService.createAuthor(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    /* Updates Current Author */
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthorById(@PathVariable Long id, @Valid @RequestBody Author author){
        Author updatedAuthor = authorService.updateAuthor(author, id);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    /* Delete Current Author */
    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable Long id){
        Author author = authorService.deleteAuthor(id);
        return ResponseEntity.ok(author);
    }
}
