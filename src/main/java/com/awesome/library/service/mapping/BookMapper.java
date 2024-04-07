package com.awesome.library.service.mapping;

import java.time.ZonedDateTime;

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

    @Mapping(target="isbn", source="isbn")
    @Mapping(target="title", source="title")
    @Mapping(target="description", source="description")
    @Mapping(target="authors", source="authors")
    @Mapping(target="publishDate", source="publishDate")
    public abstract BookResponse map(Book input);

}
