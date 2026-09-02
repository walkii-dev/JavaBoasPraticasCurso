package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidarSeTutorAguardaOutraAvaliacao implements AdocaoValidavel{

    private final AdocaoRepository adocaoRepository;

    public ValidarSeTutorAguardaOutraAvaliacao(AdocaoRepository adocaoRepository){
        this.adocaoRepository = adocaoRepository;
    }

    public void validar(SolicitacaoAdocaoDTO dto){
        boolean tutorAguardaOutraAvaliacao = adocaoRepository
                .existsByTutorIdAndStatus(dto.idTutor(),
                        StatusAdocao.AGUARDANDO_AVALIACAO);

            if (tutorAguardaOutraAvaliacao) {
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
    }
}
