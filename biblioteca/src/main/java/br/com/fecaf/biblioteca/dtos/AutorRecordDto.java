package br.com.fecaf.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;

public record AutorRecordDto(@NotBlank String name, @NotBlank String surname, @NotBlank String cellphone,
                             @NotBlank String email, @NotBlank String birthDate) {
}
