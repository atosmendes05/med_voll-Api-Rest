package med.voll.api.domain.DTO;

import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;

public record DadosMedicoList(Long id, String nome , String email, String crm , Especialidade especialidade) {

    public DadosMedicoList(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
