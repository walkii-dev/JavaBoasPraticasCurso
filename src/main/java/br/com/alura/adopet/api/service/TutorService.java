package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosCadastroTutorDTO;
import br.com.alura.adopet.api.dto.DadosTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    public TutorService(TutorRepository tutorRepository){
        this.tutorRepository = tutorRepository;
    }

    public void cadastrarTutor(DadosCadastroTutorDTO dto){
        Tutor tutor = new Tutor(dto);

        boolean telefoneJaCadastrado = tutorRepository.existsByTelefone(tutor.getTelefone());
        boolean emailJaCadastrado = tutorRepository.existsByEmail(tutor.getEmail());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            tutorRepository.save(tutor);
        }
    }

    public void atualizarDadosTutor(DadosTutorDTO dto){
        Tutor tutor = new Tutor(dto);
        tutorRepository.save(tutor);
    }
}
