package med.voll.api.controller;

import med.voll.api.domain.DTO.DadosAgendamentoConsultas;
import med.voll.api.domain.DTO.DadosDetalhadosConsulta;
import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsultas> dadosAgendamentoJson;

    @Autowired
    private JacksonTester<DadosDetalhadosConsulta> dadosDetalhadosConsultaJson;

    @MockitoBean
    private ConsultaService consulta;


    @Test
    @DisplayName("deveria retorna codigo 400 quando as informacoes estao invalidas")
    @WithMockUser
    void agendar_canario01() throws Exception {

        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("deveria retorna codigo 200 quando as informacoes estao validas")
    @WithMockUser
    void agendar_canario02() throws Exception {
        var data  = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamentos = new DadosDetalhadosConsulta(null,2l,4l,data);
        when(consulta.agendarConsulta(any())).thenReturn(dadosDetalhamentos);

        var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoJson.write(
                                new DadosAgendamentoConsultas(2l,4l,data,especialidade)
                        ).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhadosConsultaJson.write(dadosDetalhamentos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }



}