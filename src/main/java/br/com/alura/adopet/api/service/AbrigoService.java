package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;


public class AbrigoService {

    private final AbrigoRepository repository;

    public AbrigoService(AbrigoRepository repository){
        this.repository = repository;
    }

    public void cadastrar(Abrigo abrigo){
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
