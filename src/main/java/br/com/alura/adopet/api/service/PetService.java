package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosPetDTO;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    public PetService (PetRepository petRepository){
        this.petRepository = petRepository;
    }

    public List<DadosPetDTO> listarTodosOsPets(){
        return petRepository.findAllByAdotadoIsFalse()
                .stream()
                .map(pet -> new DadosPetDTO(
                        pet.getTipo().name(),
                        pet.getNome(),
                        pet.getRaca(),
                        pet.getIdade(),
                        pet.getCor(),
                        pet.getPeso()))
                .toList();
    }
}
