package com.awesome.library.service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.domain.response.BookResponse;
import com.awesome.library.service.service.LibraryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController("/book")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping(
        value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> create(final @Valid @RequestBody BookRequest request) {
        
        libraryService.create(request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
        value = "/",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponse> get(final @NotBlank @RequestParam String isbn) {

        return ResponseEntity.ok(libraryService.get(isbn));
    }

    @PutMapping(
        value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponse> put(final @Valid @RequestBody BookRequest request) {

        libraryService.put(request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
        value = "/"
    )
    public ResponseEntity<BookResponse> delete(final @NotBlank @RequestParam String isbn) {

        libraryService.delete(isbn);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
        value = "/search",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<BookResponse>> search(final @RequestParam String query, 
        final @RequestParam(required=false) Integer page, @RequestParam(required=false) Integer size) {

        return ResponseEntity.ok(libraryService.search(query, page, size));
    }
    
}
