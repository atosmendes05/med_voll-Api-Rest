package med.voll.api.domain.DTO;

import med.voll.api.domain.model.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhadosConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {

    public DadosDetalhadosConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(),consulta.getData());
    }

}
