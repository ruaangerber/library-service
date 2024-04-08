package com.awesome.library.service.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class Author {

    @Schema(description = "The name of the author.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Alan Turing"
    )
    @NotBlank
    private String name;

}
