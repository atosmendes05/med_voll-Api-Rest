package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class validacaoHorarioAntecedencia implements ValidadorAgendamento {

    public void validar(DadosAgendamentoConsultas dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenciaEmMinutos = Duration.between(agora,dataConsulta).toMinutes();

        if (diferenciaEmMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência minima de 30 minutos");
        }
    }
}
