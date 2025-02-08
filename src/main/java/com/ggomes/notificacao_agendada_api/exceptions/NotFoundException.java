package com.ggomes.notificacao_agendada_api.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(Long id) {
        super("Agendamento com ID " + id + " não encontrado.");
    }
}
