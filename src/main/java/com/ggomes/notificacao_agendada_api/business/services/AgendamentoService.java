package com.ggomes.notificacao_agendada_api.business.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoRequestDTO;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoResponseDTO;
import com.ggomes.notificacao_agendada_api.exceptions.InvalidException;
import com.ggomes.notificacao_agendada_api.exceptions.NotFoundException;
import com.ggomes.notificacao_agendada_api.infrastructure.entities.AgendamentoEntity;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;
import com.ggomes.notificacao_agendada_api.infrastructure.repositories.AgendamentoRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;


    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO request) {
        if (request.dataEnvio().isBefore(LocalDateTime.now())) {
            throw new InvalidException("A data de envio deve ser futura.");
        }

        AgendamentoEntity agendamento = AgendamentoEntity.builder()
            .dataEnvio(request.dataEnvio())
            .destinatario(request.destinatario())
            .telefone(request.telefone())
            .mensagem(request.mensagem())
            .tipoComunicacao(request.tipoComunicacao())
            .status(StatusAgendamento.PENDENTE)
            .build();

        AgendamentoEntity savedAgendamento = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDTO(
            savedAgendamento.getId(),
            savedAgendamento.getDataEnvio(),
            savedAgendamento.getDestinatario(),
            savedAgendamento.getTelefone(), 
            savedAgendamento.getMensagem(),
            savedAgendamento.getStatus()
        );
    }
    
    public AgendamentoResponseDTO buscarAgendamento(Long id) {
        AgendamentoEntity agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return new AgendamentoResponseDTO(
            agendamento.getId(),
            agendamento.getDataEnvio(),
            agendamento.getDestinatario(),
            agendamento.getTelefone(),
            agendamento.getMensagem(),
            agendamento.getStatus()
        );
    }

}