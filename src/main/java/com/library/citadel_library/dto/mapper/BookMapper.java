package com.library.citadel_library.dto.mapper;

import com.library.domain.Book;
import com.library.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);
    Book bookDTOToBook(BookDTO bookDto);

}
