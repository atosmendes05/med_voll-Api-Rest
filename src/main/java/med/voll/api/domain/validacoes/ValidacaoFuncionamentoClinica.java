package med.voll.api.domain.validacoes;


import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacaoFuncionamentoClinica implements ValidadorAgendamento {

    public void validar(DadosAgendamentoConsultas dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
        var antesDaAberturaClinica = dataConsulta.getHour() < 7;
        var depoisEncerramentoClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaClinica || depoisEncerramentoClinica ){
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clínica");
        }

    }
}
