package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.DTO.DadosPaciente;
import med.voll.api.domain.DTO.DadosPacienteList;
import med.voll.api.domain.model.Paciente;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    PacienteRepository repository;


    @PostMapping
    @Transactional
    public void cadastraPaciente(@RequestBody @Valid DadosPaciente paciente){
        repository.save(new Paciente(paciente));
    }

    @GetMapping
    public Page<DadosPacienteList> listaPacientes(@PageableDefault(size = 10,sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao).map(DadosPacienteList::new);
    }




}
