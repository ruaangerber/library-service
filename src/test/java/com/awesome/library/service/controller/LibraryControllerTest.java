package com.awesome.library.service.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.awesome.library.service.domain.common.Author;
import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;
import com.awesome.library.service.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest
@AutoConfigureMockMvc
class LibraryControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibraryService libraryService;

    // Negative scenarios

    @Test
    void createBookRequest_whenProvidedWithNull_thenThrowBadRequest() throws Exception {
        this.mockMvc.perform(post("/book").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Invalid input")));
    }

    @Test
    void createBookRequestwhenProvidedWithBlankBookDetails_thenThrowBadRequest() throws Exception {

        var input = BookRequest.builder().build();

        this.mockMvc.perform(post("/book").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("must not be blank")));
    }

    @Test
    void putBookRequest_whenProvidedWithNull_thenThrowBadRequest() throws Exception {
        this.mockMvc.perform(put("/book").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Invalid input")));
    }

    @Test
    void putBookRequestwhenProvidedWithBlankBookDetails_thenThrowBadRequest() throws Exception {

        var input = BookRequest.builder().build();

        this.mockMvc.perform(put("/book").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("must not be blank")));
    }

    @Test
    void getBook_whenProvidedWithBlankBookDetails_thenThrowBadRequest() throws Exception {
        this.mockMvc.perform(get("/book").param("isbn", ""))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("must not be blank")));
    }

    @Test
    void getBook_whenProvidedWithNull_thenThrowBadRequest() throws Exception {
        this.mockMvc.perform(get("/book"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Invalid input")));
    }

    // Positive scenarios

    @Test
    void create_whenProvidedWithBookDetails_thenReturn204() throws Exception {

        var input = BookRequest.builder()
            .isbn("123")
            .title("Hello World")
            .description("Some description")
            .publishYear(1234)
            .authors(List.of(Author.builder().name("John Doe").build()))
            .build();

        Mockito.doNothing().when(libraryService).create(Mockito.any(BookRequest.class));

        this.mockMvc.perform(post("/book").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        Mockito.verify(libraryService, Mockito.times(1)).create(Mockito.any(BookRequest.class));
    }

    @Test
    void get_whenProvidedWithBookDetailsCreateBookRequest_thenReturn200() throws Exception {
     
        var expectedResponse = BookResponse.builder().isbn("123").build();

        Mockito.when(libraryService.get(Mockito.anyString())).thenReturn(expectedResponse);

        this.mockMvc.perform(get("/book").param("isbn", "123"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        Mockito.verify(libraryService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    void put_whenProvidedWithBookDetails_thenReturn204() throws Exception {

        var input = BookRequest.builder()
            .isbn("123")
            .title("Hello World")
            .description("Some description")
            .publishYear(1234)
            .authors(List.of(Author.builder().name("John Doe").build()))
            .build();

        Mockito.doNothing().when(libraryService).put(Mockito.any(BookRequest.class));

        this.mockMvc.perform(put("/book").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        Mockito.verify(libraryService, Mockito.times(1)).put(Mockito.any(BookRequest.class));
    }

    @Test
    void delete_whenProvidedWithBookDetails_thenReturn204() throws Exception {

        Mockito.doNothing().when(libraryService).delete(Mockito.anyString());

        this.mockMvc.perform(delete("/book").param("isbn", "123"))
            .andExpect(status().isNoContent());

        Mockito.verify(libraryService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    void search_whenTitleIsProvided_thenReturn200() throws Exception {
        var expectedResponse = List.of(BookResponse.builder().isbn("123").build());

        Mockito.when(libraryService.search(Mockito.anyString(), 
            Mockito.isNull(), Mockito.isNull())).thenReturn(expectedResponse);

        this.mockMvc.perform(get("/book/search").param("query", "hello world"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        Mockito.verify(libraryService, Mockito.times(1))
            .search(Mockito.anyString(), Mockito.isNull(), Mockito.isNull());
    }

    @Test
    void search_whenTitleAndPageAndSizeIsProvided_thenReturn200() throws Exception {
        var expectedResponse = List.of(BookResponse.builder().isbn("123").build(), BookResponse.builder().isbn("124").build());

        Mockito.when(libraryService.search(Mockito.anyString(), 
            Mockito.anyInt(), Mockito.anyInt())).thenReturn(List.of(expectedResponse.get(1)));

        this.mockMvc.perform(get("/book/search")
            .param("query", "hello world")
            .param("page", "1")
            .param("size", "1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(expectedResponse.get(1)))));

        Mockito.verify(libraryService, Mockito.times(1))
            .search(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
    }


}
