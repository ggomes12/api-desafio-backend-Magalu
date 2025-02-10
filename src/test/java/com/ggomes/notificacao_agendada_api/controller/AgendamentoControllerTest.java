package com.ggomes.notificacao_agendada_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggomes.notificacao_agendada_api.business.services.AgendamentoService;
import com.ggomes.notificacao_agendada_api.controllers.AgendamentoController;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoRequestDTO;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoResponseDTO;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.TipoComunicacao;

@WebMvcTest(AgendamentoController.class)
@ExtendWith(MockitoExtension.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private AgendamentoRequestDTO requestDTO;
    private AgendamentoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AgendamentoRequestDTO(
                LocalDateTime.now().plusDays(1),
                "usuario@email.com",
                "+5511987654321",
                "Teste de mensagem",
                TipoComunicacao.EMAIL
        );

        responseDTO = new AgendamentoResponseDTO(
                1L,
                requestDTO.dataEnvio(),
                requestDTO.destinatario(),
                requestDTO.telefone(),
                requestDTO.mensagem(),
                StatusAgendamento.PENDENTE
        );

        when(agendamentoService.criarAgendamento(any(AgendamentoRequestDTO.class))).thenReturn(responseDTO);
        when(agendamentoService.buscarAgendamento(1L)).thenReturn(responseDTO);
        when(agendamentoService.cancelarAgendamento(1L)).thenReturn(responseDTO);
    }

    @Test
    void deveCriarAgendamento() throws Exception {
        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) 
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("usuario@email.com"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveBuscarAgendamento() throws Exception {
        mockMvc.perform(get("/agendamentos/1")
                .accept(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("usuario@email.com"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveCancelarAgendamento() throws Exception {
        mockMvc.perform(patch("/agendamentos/1/cancelar")
                .accept(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.status").value("PENDENTE")); 
    }


    @TestConfiguration
    static class Config {
        @Bean
        public AgendamentoService agendamentoService() {
            return Mockito.mock(AgendamentoService.class);
        }
    }
}
