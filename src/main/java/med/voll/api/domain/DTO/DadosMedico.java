package med.voll.api.domain.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.model.Especialidade;

public record DadosMedico(
        @NotBlank
                           String nome,

        @NotBlank
                           @Email
                           String email,

        @NotBlank
                           String telefone,

        @NotBlank
                           @Pattern(regexp = "\\d{4,6}")
                           String crm,

        @NotNull
                           Especialidade especialidade,

        DadosEndereco endereco) {
}
