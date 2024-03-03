package br.com.fecaf.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRecordDto(@NotBlank String title, @NotBlank String publisher, @NotBlank String preface,
                             @NotNull int pages, @NotBlank String genre, @NotBlank String author,
                             @NotNull double purchaseValue, @NotNull double saleValue, @NotNull int stock,
                             @NotBlank String dateEntry) {
}
