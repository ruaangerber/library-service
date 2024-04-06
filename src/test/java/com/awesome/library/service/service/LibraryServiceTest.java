package com.awesome.library.service.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.awesome.library.service.domain.model.Book;
import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;
import com.awesome.library.service.exception.NotFoundException;
import com.awesome.library.service.mapping.BookMapper;
import com.awesome.library.service.repository.BookRepository;
import com.awesome.library.service.service.impl.LibraryServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl(bookMapper, bookRepository);
    }

    @AfterEach
    void tearDown() {
        libraryService = null;
    }

    // Negative Cases

    @Test
    void get_whenWrongBookingIsbn_thenThrowNotFoundException() {

        when(bookRepository.findByIsbn(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> libraryService.get("123"));

        verify(bookRepository, times(1)).findByIsbn(anyString());
    }

    // Positive Cases

    @Test
    void create_whenBookingRequest_thenSave() {
        var bookRequest = BookRequest.builder().build();
        var book = Book.builder().title("Hello World").build();

        when(bookMapper.map(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        libraryService.create(bookRequest);

        verify(bookMapper, times(1)).map(any(BookRequest.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void get_whenBookingIsbn_thenRespond() {
        
        var book = Book.builder().title("Hello World").build();
        var bookResponse = BookResponse.builder().isbn("123").build();

        when(bookRepository.findByIsbn(anyString())).thenReturn(book);
        when(bookMapper.map(any(Book.class))).thenReturn(bookResponse);

        var response = libraryService.get("123");

        verify(bookRepository, times(1)).findByIsbn(anyString());

        assertEquals("123", response.getIsbn());
    }

}
