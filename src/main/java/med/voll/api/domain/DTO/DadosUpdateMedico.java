package med.voll.api.domain.DTO;

import jakarta.validation.constraints.NotNull;

public record DadosUpdateMedico(
                                @NotNull
                                Long id,
                                String nome,
                                String telefone ,
                                DadosEndereco endereco) {
}
