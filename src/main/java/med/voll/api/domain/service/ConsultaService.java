package med.voll.api.domain.service;


import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.DTO.DadosDetalhadosConsulta;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.domain.validacoes.ValidadorAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository ConsultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamento> validadores;

    public DadosDetalhadosConsulta agendarConsulta(DadosAgendamentoConsultas dados){

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw  new ValidacaoException("id do paciente não existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("id do medico não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolheMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não existe medico disponivel nessa data!");
        }
        var consulta = new Consulta(null,medico,paciente,dados.data());

        ConsultaRepository.save(consulta);

        return new DadosDetalhadosConsulta(consulta);
    }



    private Medico escolheMedico(DadosAgendamentoConsultas dados) {

        if(dados.idMedico() != null){
            return medicoRepository.findById(dados.idMedico()).get();
        }

        if(dados.especialidaade() == null ){
            throw new ValidacaoException("Especialidade é obrigatoria quando médico não for escolhido!");
        }

        return  medicoRepository.escolheMedicoPorEspecialidade(dados.especialidaade(),dados.data());

    }


}
