package com.awesome.library.service.mapping;

import java.time.ZonedDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.awesome.library.service.domain.model.Book;
import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class})
public interface BookMapper {
    
    @Mapping(target="id", ignore=true)
    @Mapping(target="updated", ignore=true)
    @Mapping(target="created", expression="java(ZonedDateTime.now())")
    public abstract Book map(BookRequest input);

    public abstract BookResponse map(Book input);

    public abstract List<BookResponse> map(List<Book> input);

}
