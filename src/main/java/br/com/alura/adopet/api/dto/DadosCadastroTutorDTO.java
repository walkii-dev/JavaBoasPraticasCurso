package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroTutorDTO(@NotBlank String nome,
                                    @NotBlank String telefone,
                                    @NotBlank @Email String email) {
}
