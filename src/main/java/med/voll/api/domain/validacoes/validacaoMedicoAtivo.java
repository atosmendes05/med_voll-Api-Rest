package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import med.voll.api.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class validacaoMedicoAtivo implements ValidadorAgendamento {

    @Autowired
    MedicoRepository repository;

    public void validar(DadosAgendamentoConsultas dados){
        if (dados.idMedico() == null){
            return;
        }

        var medicoAtivo = repository.findAtivoById(dados.idMedico());
        System.out.println(medicoAtivo);
        if (!medicoAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com esse médico");
        }

    }
}
