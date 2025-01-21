package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;

public interface ValidadorAgendamento {

    void validar(DadosAgendamentoConsultas dados);
}
