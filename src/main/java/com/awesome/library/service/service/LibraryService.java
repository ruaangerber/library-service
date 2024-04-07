package com.awesome.library.service.service;

import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;

public interface LibraryService {
    
    void create(BookRequest book);

    BookResponse get(String isbn);

    void put(BookRequest book);

    void delete(String isbn);

}
