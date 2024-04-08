package com.awesome.library.service.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Tag(name = "Library", description = "Provides CRUD operations as well as search functionality for books in a library.")
@Validated
@RestController(value = "/")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @Operation(
      summary = "Creates a book in the library database.",
      description = "Creates a book in the library database."
    )
    @ApiResponses({
      @ApiResponse(responseCode = "204")
    })
    @PostMapping(
        value = "/book",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> create(final @Valid @RequestBody BookRequest request) {
        
        libraryService.create(request);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieves a book from the library database.",
            description = "Retrieves a book from the library database.",
            parameters = {
                    @Parameter(name = "isbn", description = "The ISBN of the book.", example = "1234-1234")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = BookResponse.class),
                            mediaType = "application/json") })
    })
    @GetMapping(
        value = "/book",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponse> get(final @NotBlank @RequestParam String isbn) {

        return ResponseEntity.ok(libraryService.get(isbn));
    }

    @Operation(
            summary = "Updates a book in the library database.",
            description = "Updates a book in the library database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204")
    })
    @PutMapping(
        value = "/book",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookResponse> put(final @Valid @RequestBody BookRequest request) {

        libraryService.put(request);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deletes a book from the library database.",
            description = "Deletes a book from the library database.",
            parameters = {
                    @Parameter(name = "isbn", description = "The ISBN of the book.", example = "1234-1234")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204")
    })
    @DeleteMapping(
        value = "/book"
    )
    public ResponseEntity<BookResponse> delete(final @NotBlank @RequestParam String isbn) {

        libraryService.delete(isbn);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Searches for books in the library database.",
            description = "Searches books in the library database.",
            parameters = {
                    @Parameter(name = "query",
                            description = "A search string that contains a partial or full name of any of the values for any of the fields in the response.",
                            example = "Alan Tur"),
                    @Parameter(name = "page",
                            description = "The page number starting from 0. Used to paginate through the search results",
                            example = "0"),
                    @Parameter(name = "size",
                            description = "The number of records to return per page. Used to paginate through the search results",
                            example = "10")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = BookResponse.class))))
    })
    @GetMapping(
        value = "/book/search",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<BookResponse>> search(final @RequestParam String query, 
        final @RequestParam(required=false) Integer page, @RequestParam(required=false) Integer size) {

        return ResponseEntity.ok(libraryService.search(query, page, size));
    }
    
}
