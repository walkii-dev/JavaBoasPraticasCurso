package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarSePetAguardaOutraAvaliacao {
    private final AdocaoRepository adocaoRepository;
    private final PetRepository petRepository;

    public ValidarSePetAguardaOutraAvaliacao (AdocaoRepository adocaoRepository,
                                              PetRepository petRepository){
        this.adocaoRepository = adocaoRepository;
        this.petRepository = petRepository;
    }

    public void validar(SolicitacaoAdocaoDTO dto){

        List<Adocao> adocoes = adocaoRepository.findAll();
        Pet pet = petRepository.getReferenceById(dto.idPet());
        for (Adocao a : adocoes) {
            if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
            }
        }
    }
}
