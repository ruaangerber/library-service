package com.awesome.library.service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awesome.library.service.domain.request.BookRequest;
import com.awesome.library.service.service.LibraryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    
}
