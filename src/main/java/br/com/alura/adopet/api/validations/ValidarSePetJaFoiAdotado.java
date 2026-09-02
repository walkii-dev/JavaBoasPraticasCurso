package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidarSePetJaFoiAdotado {

    private final PetRepository petRepository;

    public ValidarSePetJaFoiAdotado (PetRepository petRepository){
        this.petRepository = petRepository;
    }

    public void validar(SolicitacaoAdocaoDTO dto){
        Pet pet = petRepository.getReferenceById(dto.idPet());

        if (pet.getAdotado() == true) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
