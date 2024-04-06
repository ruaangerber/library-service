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
import com.awesome.library.service.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
    void whenProvidedWithNullCreateBookRequest_thenThrowBadRequest() throws Exception {
        this.mockMvc.perform(post("/").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("Invalid input")));
    }

    @Test
    void whenProvidedWithBlankBookDetailsCreateBookRequest_thenThrowBadRequest() throws Exception {

        var input = BookRequest.builder().build();

        this.mockMvc.perform(post("/").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("must not be blank")));
    }

    // Positive scenarios

    @Test
    void whenProvidedWithBookDetailsCreateBookRequest_thenReturn204() throws Exception {

        var input = BookRequest.builder()
            .isbn("123")
            .title("Hello World")
            .description("Some description")
            .publishDate(LocalDate.parse("2024-04-05"))
            .authors(List.of(Author.builder().name("John Doe").build()))
            .build();

        Mockito.doNothing().when(libraryService).create(Mockito.any(BookRequest.class));

        this.mockMvc.perform(post("/").accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        Mockito.verify(libraryService, Mockito.times(1)).create(Mockito.any(BookRequest.class));
    }

}
