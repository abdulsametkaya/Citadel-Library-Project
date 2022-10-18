package com.library.citadel_library.repository;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.library.citadel_library.dto.BookDTO(book) from Book book where (book.name = :query or book.author.name = :query or book.isbn =:query or :query is null ) and (book.category.id = :cat or :cat is null ) and (book.author.id = :author or :author is null ) and (book.publisher.id = :publisher or :publisher is null )")
    Page<BookDTO> findByQueryAndCatAndAuthorAndPublisherWithPage(@Param("query") Optional<String> query,
                                                                 @Param("cat") Optional<Long> cat,
                                                                 @Param("author") Optional<Long> author,
                                                                 @Param("publisher") Optional<Long> publisher, Pageable pageable);

    @Query("SELECT u FROM Book u WHERE u.category.id = ?1")
    Book existsBookCategoryId(Long id);

    @Query("SELECT p FROM Book p WHERE p.publisher.id = ?1")
    Book existsBookPublisherId(Long id);

    @Query("SELECT a FROM Book a WHERE a.author.id = ?1")
    Book existsBookAuthorId(Long id);

}
