package com.ggomes.notificacao_agendada_api.controllers.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;

public record AgendamentoResponseDTO(
	    Long id,
	    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	    LocalDateTime dataEnvio,
	    String destinatario,
	    String telefone,
	    String mensagem,
	    StatusAgendamento status
	) {}