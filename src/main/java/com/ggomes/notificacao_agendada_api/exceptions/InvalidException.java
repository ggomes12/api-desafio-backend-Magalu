package com.ggomes.notificacao_agendada_api.exceptions;

public class InvalidException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidException(String message) {
		super(message);
	}

}
