package com.library.citadel_library.service;

import com.library.citadel_library.domain.*;
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
    ImageFileRepository imageRepository;
    BookMapper bookMapper;

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

        Book foundBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        if (foundBook.getBuiltIn()) {
            throw new RuntimeException(String.format(ErrorMessage.BOOK_NOT_AVAILABLE_TO_REMOVE_MESSAGE,id));
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

        if (!book.getLoanable() && book.getBuiltIn()) {
            throw new BadRequestException(String.format(ErrorMessage.BOOK_NOT_AVAILABLE_TO_REMOVE_MESSAGE, id));
        }
        book.setActive(false);
        bookRepository.save(book);
        BookDTO bookDTO = bookMapper.bookToBookDTO(book);

        return bookDTO;
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> findAllWithPage(Optional<String> query, Optional<Long> categoryId, Optional<Long> authorId,
                                         Optional<Long> publisherId, Pageable pageable) {

        Page<BookDTO> book;

        if (query.isPresent() || categoryId.isPresent() || authorId.isPresent() || publisherId.isPresent()) {
            book = bookRepository.findByQueryAndCatAndAuthorAndPublisherWithPage(query, categoryId,
                    authorId, publisherId, pageable);
            return book;
        } else {
            throw new BadRequestException(ErrorMessage.INVALID_BOOK_PARAMETER_MESSAGE);
        }
    }

    private void bookForeign(Book book, BookDTO bookDto){
        Category category = categoryRepository.findById(bookDto.getCategory_id()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookDto.getCategory_id())));
        Author author = authorRepository.findById(bookDto.getAuthor_id()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookDto.getAuthor_id())));
        Publisher publisher = publisherRepository.findById(bookDto.getPublisher_id()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookDto.getPublisher_id())));
        ImageFile image = imageRepository.findById(bookDto.getImage_id()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, bookDto.getImage_id())));

        book.setCategory(category);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setImage(image);
    }
}
