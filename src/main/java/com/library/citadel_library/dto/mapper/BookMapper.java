package com.library.citadel_library.dto.mapper;

import com.library.citadel_library.domain.Book;
import com.library.citadel_library.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface BookMapper {

    public BookDTO bookToBookDTO(Book book);

    public Book bookDTOToBook(BookDTO bookDto);


}
