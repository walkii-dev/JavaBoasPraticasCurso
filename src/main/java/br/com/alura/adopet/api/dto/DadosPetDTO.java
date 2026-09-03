package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosPetDTO(@NotNull String tipoPet,
                          @NotBlank String nome,
                          @NotBlank String raca,
                          @NotNull Integer idade,
                          @NotBlank String cor,
                          @NotNull Float peso) {
}
