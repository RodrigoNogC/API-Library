package br.com.fecaf.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;

public record GeneroRecordDto(@NotBlank String name) {
}
