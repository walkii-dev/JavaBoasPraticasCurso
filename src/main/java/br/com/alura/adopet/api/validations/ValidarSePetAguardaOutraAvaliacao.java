package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidarSePetAguardaOutraAvaliacao implements AdocaoValidavel{
    private final AdocaoRepository adocaoRepository;

    public ValidarSePetAguardaOutraAvaliacao(AdocaoRepository adocaoRepository){
        this.adocaoRepository = adocaoRepository;
    }

    public void validar(SolicitacaoAdocaoDTO dto){
        boolean petAguardaOutraAvaliacao = adocaoRepository
                .existsByPetIdAndStatus(dto.idPet(),
                        StatusAdocao.AGUARDANDO_AVALIACAO);

            if (petAguardaOutraAvaliacao) {
                throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
            }
    }
}
