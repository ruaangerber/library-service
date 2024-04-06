package com.awesome.library.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.awesome.library.service.domain.model.Book;
import com.awesome.library.service.domain.request.BookRequest;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    @Mapping(target="id", ignore=true)
    public abstract Book map(BookRequest input);

}
