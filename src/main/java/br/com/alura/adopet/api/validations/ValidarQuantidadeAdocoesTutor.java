package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidarQuantidadeAdocoesTutor implements AdocaoValidavel{

    private final AdocaoRepository adocaoRepository;
    public ValidarQuantidadeAdocoesTutor(AdocaoRepository adocaoRepository){
        this.adocaoRepository = adocaoRepository;
    }

    public void validar(SolicitacaoAdocaoDTO dto){
        Long quantidadeAdocoesTutor = adocaoRepository
                .countByTutorIdAndStatus(
                        dto.idTutor(),
                        StatusAdocao.APROVADO);

            if (quantidadeAdocoesTutor > 4) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
            }
    }
}
