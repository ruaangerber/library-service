package com.awesome.library.service.domain.request;

import java.time.LocalDate;
import java.util.List;

import com.awesome.library.service.domain.common.Author;

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
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private List<Author> authors;

    @NotNull
    private LocalDate publishDate;

}
