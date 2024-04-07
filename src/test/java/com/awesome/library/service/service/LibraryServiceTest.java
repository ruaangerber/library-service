package com.awesome.library.service.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.awesome.library.service.domain.model.Book;
import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;
import com.awesome.library.service.exception.AlreadyExistsException;
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

import java.util.List;

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
    void create_whenExistingBookingIsbn_thenThrowAlreadyExistsException() {

        var bookRequest = BookRequest.builder().isbn("123").build();
        var book = Book.builder().isbn("123").build();

        when(bookMapper.map(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.findByIsbn(anyString())).thenReturn(book);

        assertThrows(AlreadyExistsException.class, () -> libraryService.create(bookRequest));

        verify(bookMapper, times(1)).map(any(BookRequest.class));
        verify(bookRepository, times(1)).findByIsbn(anyString());
    }

    @Test
    void get_whenWrongBookingIsbn_thenThrowNotFoundException() {

        when(bookRepository.findByIsbn(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> libraryService.get("123"));

        verify(bookRepository, times(1)).findByIsbn(anyString());
    }

    @Test
    void put_whenNotFoundByIsbn_thenNotFoundException() {

        var bookRequest = BookRequest.builder().build();
        var book = Book.builder().isbn("123").title("Hello World").build();

        when(bookMapper.map(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.findByIsbn(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> libraryService.put(bookRequest));

        verify(bookMapper, times(1)).map(any(BookRequest.class));
        verify(bookRepository, times(1)).findByIsbn(anyString());
    }

    @Test
    void delete_whenNotFoundByIsbn_thenNotFoundException() {

        when(bookRepository.findByIsbn(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> libraryService.delete("123"));

        verify(bookRepository, times(1)).findByIsbn(anyString());
    }

    // Positive Cases

    @Test
    void create_whenBookingRequest_thenSave() {
        var bookRequest = BookRequest.builder().build();
        var book = Book.builder().isbn("123").title("Hello World").build();

        when(bookMapper.map(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.findByIsbn(anyString())).thenReturn(null);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        libraryService.create(bookRequest);

        verify(bookMapper, times(1)).map(any(BookRequest.class));
        verify(bookRepository, times(1)).findByIsbn(anyString());
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

    @Test
    void put_whenBookingRequestExists_thenSave() {
        var bookRequest = BookRequest.builder().build();
        var book = Book.builder().isbn("123").title("Hello World").build();

        when(bookMapper.map(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.findByIsbn(anyString())).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        libraryService.put(bookRequest);

        verify(bookMapper, times(1)).map(any(BookRequest.class));
        verify(bookRepository, times(1)).findByIsbn(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void delete_whenBookingRequestExists_thenDelete() {
        var book = Book.builder().isbn("123").title("Hello World").build();

        when(bookRepository.findByIsbn(anyString())).thenReturn(book);
        Mockito.doNothing().when(bookRepository).delete(any(Book.class));

        libraryService.delete("123");

        verify(bookRepository, times(1)).findByIsbn(anyString());
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    void search_whenTitleProvidedAndExists_thenReturn() {
        var books = List.of(Book.builder().isbn("123").title("Hello World").build());
        var bookResponse = List.of(BookResponse.builder().isbn("123").build());

        when(bookRepository.findByAny(anyString())).thenReturn(books);
        when(bookMapper.map(any(List.class))).thenReturn(bookResponse);

        var response = libraryService.search("123");

        verify(bookRepository, times(1)).findByAny(anyString());

        assertEquals(1, response.size());
        assertEquals("123", response.get(0).getIsbn());
    }

}
