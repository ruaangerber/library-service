package com.awesome.library.service.domain.response;

import java.util.List;

import com.awesome.library.service.domain.common.Author;

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
public class BookResponse {
    
    private String isbn;

    private String title;

    private String description;

    private List<Author> authors;

    private Integer publishYear;

}
