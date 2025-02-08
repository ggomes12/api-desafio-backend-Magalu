package com.ggomes.notificacao_agendada_api.business.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoRequestDTO;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoResponseDTO;
import com.ggomes.notificacao_agendada_api.infrastructure.entities.AgendamentoEntity;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;
import com.ggomes.notificacao_agendada_api.infrastructure.repositories.AgendamentoRepository;

@Service
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO request) {
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

    public Optional<AgendamentoResponseDTO> buscarAgendamento(Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamento -> new AgendamentoResponseDTO(
                        agendamento.getId(),
                        agendamento.getDataEnvio(),
                        agendamento.getDestinatario(),
                        agendamento.getTelefone(), 
                        agendamento.getMensagem(),
                        agendamento.getStatus()
                ));
    }

    public void removerAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }
}