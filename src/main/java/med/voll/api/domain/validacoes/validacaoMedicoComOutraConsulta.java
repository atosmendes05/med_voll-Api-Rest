package med.voll.api.domain.validacoes;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.exception_consulta.ValidacaoException;
import med.voll.api.domain.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class validacaoMedicoComOutraConsulta  implements ValidadorAgendamento{

    @Autowired
    private ConsultaRepository repository;


    public void validar(DadosAgendamentoConsultas dados){
        var medicoPossuiOutraCsonsulta = repository.existsByMedicoIdAndData(dados.idMedico(),dados.data());

        if (medicoPossuiOutraCsonsulta){
            throw new ValidacaoException("Medico ja possui outra consulta nesse mesmo horario");
        }
    }
}
