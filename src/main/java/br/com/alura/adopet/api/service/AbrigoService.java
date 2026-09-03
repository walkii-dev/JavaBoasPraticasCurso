package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosListagemAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosPetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;

public class AbrigoService {

    private final AbrigoRepository repository;

    public AbrigoService(AbrigoRepository repository){
        this.repository = repository;
    }

    public List<DadosListagemAbrigoDTO> listar(){
        return repository
                .findAll()
                .stream()
                .map( abr -> new DadosListagemAbrigoDTO(
                        abr.getNome(),
                        abr.getTelefone(),
                        abr.getEmail())).toList();
    }

    public void cadastrar(CadastroAbrigoDTO dto){

        boolean nomeJaCadastrado = repository.existsByNome(dto.nome());
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = repository.existsByEmail(dto.email());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
            repository.save(abrigo);
        }
    }

    public List<DadosPetDTO> listarPets(String idOuNome){
        try {
            Long id = Long.parseLong(idOuNome);
            return repository
                    .getReferenceById(id)
                    .getPets()
                    .stream()
                    .map(pet -> new DadosPetDTO(
                            pet.getTipo().name(),
                            pet.getNome(),
                            pet.getRaca(),
                            pet.getIdade(),
                            pet.getCor(),
                            pet.getPeso()))
                    .toList();
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Id do abrigo não encontrado!");
        } catch (NumberFormatException e) {
            try {
                return repository
                        .findByNome(idOuNome)
                        .getPets()
                        .stream()
                        .map(pet -> new DadosPetDTO(
                                pet.getTipo().name(),
                                pet.getNome(),
                                pet.getRaca(),
                                pet.getIdade(),
                                pet.getCor(),
                                pet.getPeso()
                        ))
                        .toList();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("Nome do abrigo não encontrado!");
            }
        }
    }

    public void cadastrarPetNoAbrigo(String idOuNome, DadosPetDTO dto){
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);

            Pet pet = new Pet(dto);

            pet.setAbrigo(abrigo);
            abrigo.getPets().add(pet);

        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Id do Abrigo Não encontrado!");
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                Pet pet = new Pet(dto);
                pet.setAbrigo(abrigo);
                abrigo.getPets().add(pet);
                repository.save(abrigo);
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("Nome do Abrigo Não encontrado!");
            }
        }
    }
}
