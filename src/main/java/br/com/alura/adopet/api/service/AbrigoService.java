package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosListagemAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.http.ResponseEntity;

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


        boolean nomeJaCadastrado = repository.existsByNome(abrigo.getNome());
        boolean telefoneJaCadastrado = repository.existsByTelefone(abrigo.getTelefone());
        boolean emailJaCadastrado = repository.existsByEmail(abrigo.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            repository.save(abrigo);
        }
    }
}
