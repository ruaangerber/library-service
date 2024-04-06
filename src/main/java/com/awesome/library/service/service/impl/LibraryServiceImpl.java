package com.awesome.library.service.service.impl;

import org.springframework.stereotype.Service;

import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.mapping.BookMapper;
import com.awesome.library.service.repository.BookRepository;
import com.awesome.library.service.service.LibraryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;
    
    @Override
    public void create(final BookRequest bookRequest) {
        log.info("Create request for book: [{}]", bookRequest);

        var book = bookMapper.map(bookRequest);

        bookRepository.save(book);
    }
    
}
