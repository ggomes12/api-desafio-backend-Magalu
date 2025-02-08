package com.ggomes.notificacao_agendada_api.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggomes.notificacao_agendada_api.infrastructure.entities.AgendamentoEntity;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
}
