package com.awesome.library.service.domain.request;

import java.util.List;

import com.awesome.library.service.domain.common.Author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    
    @NotBlank
    @Schema(description = "The ISBN identifier for this book.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1234-1234"
    )
    private String isbn;

    @Schema(description = "The title of the book.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "How to Program: Java"
    )
    @NotBlank
    private String title;

    @Schema(description = "The description of the book.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "This is an example description of a book."
    )
    @NotBlank
    private String description;

    @Schema(description = "A list of authors that contributed to this book.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<Author> authors;

    @Schema(description = "The year this book was published.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2024"
    )
    @NotNull
    private Integer publishYear;

}
