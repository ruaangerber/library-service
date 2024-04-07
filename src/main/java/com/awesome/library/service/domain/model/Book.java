package com.awesome.library.service.domain.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    @NotBlank
    private String isbn;

    @Field
    @NotBlank
    private String title;

    @Field
    @NotBlank
    private String description;

    @Field
    private List<Author> authors;

    @Field
    @NotNull
    private Integer publishYear;

    @Field
    @NotNull
    private ZonedDateTime created;

    @Field
    private ZonedDateTime updated;

}
