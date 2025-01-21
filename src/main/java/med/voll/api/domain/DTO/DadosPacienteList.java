package med.voll.api.domain.DTO;

import med.voll.api.domain.model.Paciente;

public record DadosPacienteList(Long id, String nome , String email , String cpf) {

    public DadosPacienteList(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(),paciente.getEmail(), paciente.getCpf());
    }

}
