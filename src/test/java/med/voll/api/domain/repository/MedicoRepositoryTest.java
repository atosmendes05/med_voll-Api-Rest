package med.voll.api.domain.repository;

import med.voll.api.domain.DTO.DadosEndereco;
import med.voll.api.domain.DTO.DadosMedico;
import med.voll.api.domain.DTO.DadosPaciente;
import med.voll.api.domain.model.Consulta;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private  TestEntityManager em ;

    @Test
    @DisplayName("Deve devolver null quando o unico medico cadastrado não estive disponivel na data")
    void escolheMedicoPorEspecialidadeCenario1() {
        //given ou arrange
        var proximaSegunda = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Atos mendes","medico@voll.med","123456",Especialidade.CARDIOLOGIA,dadosEndereco());
        var paciente = cadastrarPaciente("neto","netomendes05@gmail.com","07586453");
        cadastrarConsulta(medico,paciente,proximaSegunda);


        //when ou act
        var medicoLivre = medicoRepository.escolheMedicoPorEspecialidade(Especialidade.CARDIOLOGIA,proximaSegunda);

        //then ou assert
        assertThat(medicoLivre).isNull();
    }


    @Test
    @DisplayName("Deve devolver medico quando ele estive disponivel na data")
    void escolheMedicoPorEspecialidadeCenario2() {
        //given ou arrange
        var proximaSegunda = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        //when ou act
        var medico = cadastrarMedico("Atos mendes","medico@voll.med","123456",Especialidade.CARDIOLOGIA,dadosEndereco());

        //then ou assert
        var medicoLivre = medicoRepository.escolheMedicoPorEspecialidade(Especialidade.CARDIOLOGIA,proximaSegunda);
        assertThat(medicoLivre).isEqualTo(medico);
    }



    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        var consulta = new Consulta(null, medico, paciente, data);
        em.persist(consulta);
    }


    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade,DadosEndereco endereco) {
        var medico = new Medico(true,nome, email, crm, especialidade,endereco);
        em.persist(medico);
        em.flush();
        return medico;
    }


    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(true,nome, email, cpf,dadosEndereco());
        em.persist(paciente);
        return paciente;
    }


    private DadosMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }


    private DadosPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }


    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }









}