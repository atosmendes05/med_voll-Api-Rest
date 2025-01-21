package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class validacaoPacienteSemConsulta implements ValidadorAgendamento {

    @Autowired
    PacienteRepository repository;

    public void validar(DadosAgendamentoConsultas dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario,ultimoHorario);
        if (pacientePossuiConsulta){
            throw new ValidacaoException("Paciente ja possui consulta agendada nesse dia");
        }

    }
}
