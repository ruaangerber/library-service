package com.awesome.library.service.service;

import com.awesome.library.service.domain.request.BookRequest;

public interface LibraryService {
    
    void create(BookRequest book);

}
