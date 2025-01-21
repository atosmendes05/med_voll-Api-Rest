package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.DTO.DadosDetalhadosMedico;
import med.voll.api.domain.DTO.DadosMedico;
import med.voll.api.domain.DTO.DadosMedicoList;
import med.voll.api.domain.DTO.DadosUpdateMedico;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class    MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosMedico dados , UriComponentsBuilder uriBuild){
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuild.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhadosMedico(medico)); // retorna codigo 201 com body e header
    }


    @GetMapping
    public ResponseEntity<Page<DadosMedicoList>>listaMedicos(@PageableDefault(size = 10,sort = {"nome"})  Pageable paginacao){
        var page =  repository.findAllByAtivoTrue(paginacao).map(DadosMedicoList::new);

        return ResponseEntity.ok(page); // retorna codigo 200
    }

    @GetMapping("/{id}")
    public ResponseEntity informacaoMedicos(@PathVariable Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity medicoName(@PathVariable String nome){
        Optional<Medico>  medico = repository.buscaMedicoPorNome(nome);

        if (medico.isPresent()){
            var m = medico.get();
            return ResponseEntity.ok(new DadosDetalhadosMedico(m.getNome(),m.getEmail(),m.getTelefone(),m.getCrm(),m.getEspecialidade(),m.getEndereco()));
        }else {
            return  null;
        }

    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedico(@RequestBody @Valid DadosUpdateMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.updateMedico(dados);

        return ResponseEntity.ok(new DadosDetalhadosMedico(medico)); // retorna codigo 200 com body
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return  ResponseEntity.noContent().build();
    }


//    @DeleteMapping("{id}")
//    @Transactional
//    public void deleteMedico(@PathVariable Long id){
//        repository.deleteById(id);
//    }
    
}
