package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosListagemAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosPetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
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
                    .map(pet -> new DadosPetDTO())
                    .toList();
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Id do abrigo não encontrado!");
        } catch (NumberFormatException e) {
            try {
                return repository
                        .findByNome(idOuNome)
                        .getPets()
                        .stream()
                        .map(pet -> new DadosPetDTO())
                        .toList();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("Nome do abrigo não encontrado!");
            }
        }
    }
}
