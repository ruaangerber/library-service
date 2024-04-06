package com.awesome.library.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.awesome.library.service.domain.model.Book;
import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    @Mapping(target="id", ignore=true)
    public abstract Book map(BookRequest input);

    @Mapping(target="isbn", source="isbn")
    @Mapping(target="title", source="title")
    @Mapping(target="description", source="description")
    @Mapping(target="authors", source="authors")
    @Mapping(target="publishDate", source="publishDate")
    public abstract BookResponse map(Book input);

}
