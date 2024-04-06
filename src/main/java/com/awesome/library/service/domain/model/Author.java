package com.awesome.library.service.domain.model;

import org.springframework.data.couchbase.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    
    @Field
    @NotBlank
    private String name;

}
