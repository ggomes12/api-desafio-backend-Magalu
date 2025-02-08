package com.ggomes.notificacao_agendada_api.infrastructure.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.StatusAgendamento;
import com.ggomes.notificacao_agendada_api.infrastructure.enums.TipoComunicacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private String destinatario;
    
    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "tipo_comunicacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoComunicacao tipoComunicacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;
    
    
} 