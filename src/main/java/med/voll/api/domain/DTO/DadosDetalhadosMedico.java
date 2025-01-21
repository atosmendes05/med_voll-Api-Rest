package med.voll.api.domain.DTO;

import med.voll.api.domain.model.Endereco;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;

public record DadosDetalhadosMedico(String nome,

                                    String email,

                                    String telefone,

                                    String crm,

                                    Especialidade especialidade,

                                    Endereco endereco) {

    public DadosDetalhadosMedico(Medico medico) {
        this(medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(),medico.getEndereco());

    }

}
