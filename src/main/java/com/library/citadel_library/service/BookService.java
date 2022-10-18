package com.library.citadel_library.service;

import com.library.citadel_library.domain.Author;
import com.library.citadel_library.domain.Book;
import com.library.citadel_library.domain.Category;
import com.library.citadel_library.domain.Publisher;
import com.library.citadel_library.dto.BookDTO;
import com.library.citadel_library.dto.mapper.BookMapper;
import com.library.citadel_library.exception.BadRequestException;
import com.library.citadel_library.exception.ResourceNotFoundException;
import com.library.citadel_library.exception.message.ErrorMessage;
import com.library.citadel_library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class BookService {

    BookRepository bookRepository;

    AuthorRepository authorRepository;

    CategoryRepository categoryRepository;

    PublisherRepository publisherRepository;
    BookMapper bookMapper;

    LoanRepository loanRepository;


    public BookDTO getOneBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        BookDTO bookDTO = bookMapper.bookToBookDTO(book);

        return bookDTO;
    }

    public BookDTO createBook(BookDTO bookDto) {

        Book book = bookMapper.bookDTOToBook(bookDto);
        bookForeign(book,bookDto);

        bookRepository.save(book);

        bookDto.setId(book.getId());

        return bookDto;
    }


    public BookDTO updateBookById(Long id, BookDTO bookDTO) {

        Book foundBook = bookRepository.findById(id).orElse(null);

        if (foundBook.getBuiltIn()) {
            throw new RuntimeException("It is not permitted to change");
        }

        bookDTO.setId(id);

        foundBook = bookMapper.bookDTOToBook(bookDTO);

        bookForeign(foundBook,bookDTO);

        bookRepository.save(foundBook);

        return bookDTO;
    }

    public BookDTO deleteOneBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        if (!book.getLoanable()) {
            throw new BadRequestException(String.format(ErrorMessage.BOOK_NOT_AVAILABLE_TO_REMOVE_MESSAGE, id));
        }
        BookDTO bookDTO = bookMapper.bookToBookDTO(book);

        return bookDTO;
    }


    @Transactional(readOnly = true)
    public Page<BookDTO> findAllWithPage(Optional<String> query, Optional<Long> categoryId, Optional<Long> authorId,
                                         Optional<Long> publisherId, Pageable pageable) {

        Page<BookDTO> book = null;

        if (query.isPresent() || categoryId.isPresent() || authorId.isPresent() || publisherId.isPresent()) {
            book = bookRepository.findByQueryAndCatAndAuthorAndPublisherWithPage(query, categoryId,
                    authorId, publisherId, pageable);
            return book;
        } else {
            throw new BadRequestException(ErrorMessage.INVALID_BOOK_PARAMETER_MESSAGE);
        }
    }

    private void bookForeign(Book book, BookDTO bookDto){
        Category category = categoryRepository.findById(bookDto.getCategory_id()).orElse(null);
        Author author = authorRepository.findById(bookDto.getAuthor_id()).orElse(null);
        Publisher publisher = publisherRepository.findById(bookDto.getPublisher_id()).orElse(null);

        book.setCategory(category);
        book.setAuthor(author);
        book.setPublisher(publisher);
    }
}
