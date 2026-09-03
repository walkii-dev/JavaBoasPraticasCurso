package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosListagemAbrigoDTO;
import br.com.alura.adopet.api.dto.DadosPetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    private final AbrigoService abrigoService;
    public AbrigoController (AbrigoService abrigoService){
        this.abrigoService = abrigoService;
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemAbrigoDTO>> listar() {
        return ResponseEntity.ok(this.abrigoService.listar());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDTO dto) {
        try {
            this.abrigoService.cadastrar(dto);
            return ResponseEntity.ok().body("Abrigo criado com sucesso!");
        } catch (ValidacaoException exc){
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DadosPetDTO>> listarPets(@PathVariable String idOuNome) {
        try {
            return ResponseEntity.ok()
                    .body(this.abrigoService.listarPets(idOuNome));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            repository.save(abrigo);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                repository.save(abrigo);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
