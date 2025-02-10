package com.ggomes.notificacao_agendada_api.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ggomes.notificacao_agendada_api.business.services.AgendamentoService;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoRequestDTO;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoResponseDTO;
import com.ggomes.notificacao_agendada_api.exceptions.InvalidException;
import com.ggomes.notificacao_agendada_api.exceptions.NotFoundException;
import com.ggomes.notificacao_agendada_api.infrastructure.entities.AgendamentoEntity;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.TipoComunicacao;
import com.ggomes.notificacao_agendada_api.infrastructure.repositories.AgendamentoRepository;



@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgendamentoEntity agendamentoEntity;
    private AgendamentoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AgendamentoRequestDTO(
                LocalDateTime.now().plusDays(1),
                "usuario@email.com",
                "+5511987654321",
                "Teste de mensagem",
                TipoComunicacao.EMAIL
        );

        agendamentoEntity = AgendamentoEntity.builder()
                .id(1L)
                .dataEnvio(requestDTO.dataEnvio())
                .destinatario(requestDTO.destinatario())
                .telefone(requestDTO.telefone())
                .mensagem(requestDTO.mensagem())
                .tipoComunicacao(requestDTO.tipoComunicacao())
                .status(StatusAgendamento.PENDENTE)
                .build();
    }

    
    
    @Test
    void deveCriarAgendamentoComSucesso() {
        when(agendamentoRepository.save(Mockito.<AgendamentoEntity>any())).thenReturn(agendamentoEntity);

        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(requestDTO);

        assertNotNull(response);
        assertEquals("usuario@email.com", response.destinatario());
        assertEquals(StatusAgendamento.PENDENTE, response.status());

        verify(agendamentoRepository, times(1)).save(Mockito.<AgendamentoEntity>any());
    }

    
    
    @Test
    void deveLancarExcecaoQuandoDataForPassada() {
        AgendamentoRequestDTO requestInvalido = new AgendamentoRequestDTO(
                LocalDateTime.now().minusDays(1), 
                "usuario@email.com",
                "+5511987654321",
                "Teste de mensagem inválida",
                TipoComunicacao.EMAIL
        );

        assertThrows(InvalidException.class, () -> agendamentoService.criarAgendamento(requestInvalido));
    }

    
    
    @Test
    void deveRetornarAgendamentoExistente() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));

        AgendamentoResponseDTO response = agendamentoService.buscarAgendamento(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("usuario@email.com", response.destinatario());

        verify(agendamentoRepository, times(1)).findById(1L);
    }

    
    
    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoEncontrado() {
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> agendamentoService.buscarAgendamento(99L));

        verify(agendamentoRepository, times(1)).findById(99L);
    }

    
    
    @Test
    void deveCancelarAgendamento() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));

        AgendamentoResponseDTO response = agendamentoService.cancelarAgendamento(1L);

        assertNotNull(response);
        assertEquals(StatusAgendamento.CANCELADO, response.status());

        verify(agendamentoRepository, times(1)).save(Mockito.<AgendamentoEntity>any());
    }

    
    
    @Test
    void deveLancarExcecaoAoCancelarAgendamentoJaCancelado() {
        agendamentoEntity.setStatus(StatusAgendamento.CANCELADO);
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));

        assertThrows(InvalidException.class, () -> agendamentoService.cancelarAgendamento(1L));

        verify(agendamentoRepository, never()).save(Mockito.<AgendamentoEntity>any());
    }
}