package br.com.fecaf.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;

public record EditoraRecordDto(@NotBlank String name, @NotBlank String cnpj, @NotBlank String cellphone,
                               @NotBlank String email, @NotBlank String street, @NotBlank String district,
                               @NotBlank String zipCode, @NotBlank String city) {
}
