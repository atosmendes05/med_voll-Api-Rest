package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPacienteAtivo implements ValidadorAgendamento {

    @Autowired
    PacienteRepository repository;

    public void validar(DadosAgendamentoConsultas dados){
        if (dados.idPaciente() == null){
            return;
        }

        var pacientAtivo = repository.findAtivoById(dados.idPaciente());
        System.out.println(pacientAtivo);
        if (!pacientAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com Paciente excluido");
        }

    }
}
