package com.awesome.library.service.service.impl;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;
import com.awesome.library.service.exception.AlreadyExistsException;
import com.awesome.library.service.exception.NotFoundException;
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

        var searchForExistingBook = bookRepository.findByIsbn(book.getIsbn());

        if (Objects.nonNull(searchForExistingBook)) {
            throw new AlreadyExistsException("ISBN [{}] already exists in library".formatted(book.getIsbn()));
        }

        bookRepository.save(book);
    }

    @Override
    public BookResponse get(final String isbn) {
        log.info("Get book for isbn: [{}]", isbn);

        var book = bookRepository.findByIsbn(isbn);

        if (Objects.isNull(book)) {
            throw new NotFoundException("Book with ISBN [{}] not found in library".formatted(isbn));
        }

        log.info("Found book matching ISBN [{}]: [{}]", isbn, book);

        return bookMapper.map(book);
    }

    @Override
    public void put(final BookRequest bookRequest) {
        log.info("Update request for book: [{}]", bookRequest);

        var book = bookMapper.map(bookRequest);

        var searchForExistingBook = bookRepository.findByIsbn(book.getIsbn());

        if (Objects.isNull(searchForExistingBook)) {
            throw new NotFoundException("ISBN [{}] not found in library".formatted(book.getIsbn()));
        }

        book.setId(searchForExistingBook.getId());
        book.setUpdated(ZonedDateTime.now());

        bookRepository.save(book);
    }

    @Override
    public void delete(final String isbn) {
        log.info("Delete request for book: [{}]", isbn);

        var searchForExistingBook = bookRepository.findByIsbn(isbn);

        if (Objects.isNull(searchForExistingBook)) {
            throw new NotFoundException("ISBN [{}] not found in library".formatted(isbn));
        }

        bookRepository.delete(searchForExistingBook);
    }
    
}
