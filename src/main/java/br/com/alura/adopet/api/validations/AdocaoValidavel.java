package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;

public interface AdocaoValidavel {
    void validar(SolicitacaoAdocaoDTO dto);
}
