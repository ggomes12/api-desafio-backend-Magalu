package com.ggomes.notificacao_agendada_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggomes.notificacao_agendada_api.business.services.AgendamentoService;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoRequestDTO;
import com.ggomes.notificacao_agendada_api.controllers.dtos.AgendamentoResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@RequestBody AgendamentoRequestDTO request) {
        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(request);
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarAgendamento(@PathVariable Long id) {
        AgendamentoResponseDTO response = agendamentoService.buscarAgendamento(id);
        return ResponseEntity.ok(response);
    }
    
    
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDTO> cancelarAgendamento(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelarAgendamento(id));
    }

}
