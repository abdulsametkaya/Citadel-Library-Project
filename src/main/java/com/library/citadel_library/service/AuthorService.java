package com.library.citadel_library.service;


import com.library.citadel_library.domain.Author;
import com.library.citadel_library.exception.ConflictException;
import com.library.citadel_library.exception.ResourceNotFoundException;
import com.library.citadel_library.exception.message.ErrorMessage;
import com.library.citadel_library.repository.AuthorRepository;
import com.library.citadel_library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE,id)));
    }

    public Page<Author> getAllAuthorsWithPage(Pageable pageable){
        return authorRepository.findAllPublishersWithPage(pageable);
    }

    public void createAuthor(Author author){
        authorRepository.save(author);
    }

    public Author updateAuthor(Author author, Long id) {
        Author foundAuthor = getAuthorById(id);
        foundAuthor.setName(author.getName());
        foundAuthor.setBuiltIn(author.getBuiltIn());

        authorRepository.save(foundAuthor);
        return foundAuthor;
    }

    public Author deleteAuthor(Long id) {
        Author foundAuthor = getAuthorById(id);

        if (bookRepository.existsBookAuthorId(id) != null){
            throw new ConflictException(String.format(ErrorMessage.AUTHOR_NOT_DELETE_MESSAGE,id));
        }else{
            authorRepository.deleteById(id);
        }
        return foundAuthor;
    }

}
