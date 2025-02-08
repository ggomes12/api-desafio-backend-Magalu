package com.ggomes.notificacao_agendada_api.controllers.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.TipoComunicacao;

public record AgendamentoRequestDTO(
		@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	    LocalDateTime dataEnvio,
	    String destinatario,
	    String telefone,
	    String mensagem,
	    TipoComunicacao tipoComunicacao
	) {}