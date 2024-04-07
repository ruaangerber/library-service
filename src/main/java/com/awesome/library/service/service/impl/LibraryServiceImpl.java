package com.awesome.library.service.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
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

    private static final Integer DEFAULT_PAGE = 0;

    private static final Integer DEFAULT_SIZE = 100;
    
    @Override
    public void create(final BookRequest bookRequest) {
        log.info("Create request for book: [{}]", bookRequest);

        var book = bookMapper.map(bookRequest);

        var searchForExistingBook = bookRepository.findByIsbn(book.getIsbn());

        if (Objects.nonNull(searchForExistingBook)) {
            throw new AlreadyExistsException("ISBN [%s] already exists in library".formatted(book.getIsbn()));
        }

        bookRepository.save(book);
    }

    @Override
    public BookResponse get(final String isbn) {
        log.info("Get book for isbn: [{}]", isbn);

        var book = bookRepository.findByIsbn(isbn);

        if (Objects.isNull(book)) {
            throw new NotFoundException("Book with ISBN [%s] not found in library".formatted(isbn));
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
            throw new NotFoundException("ISBN [%s] not found in library".formatted(book.getIsbn()));
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
            throw new NotFoundException("ISBN [%s] not found in library".formatted(isbn));
        }

        bookRepository.delete(searchForExistingBook);
    }

    @Override
    public List<BookResponse> search(final String query, final Integer page, final Integer size) {
        log.info("Search query: [{}]; page: [{}]; size: [{}]", query, page, size);

        var defaultPage = Objects.isNull(page) ? DEFAULT_PAGE : page;
        var defaultSize = Objects.isNull(size) ? DEFAULT_SIZE : size;

        return bookMapper.map(bookRepository.findByAny(query, defaultPage, defaultSize));
    }
    
}
