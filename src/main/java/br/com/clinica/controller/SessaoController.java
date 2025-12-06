package br.com.clinica.controller;

import br.com.clinica.dto.EvolucaoRequest;
import br.com.clinica.dto.SessaoRequest;
import br.com.clinica.model.*;
import br.com.clinica.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<Sessao> criarSessao(@RequestBody SessaoRequest request) {
        return clinicaService.buscarPacientePorId(request.getPacienteId())
                .flatMap(paciente -> clinicaService.listarUsuarios().stream()
                        .filter(u -> u.getId().equals(request.getAlunoId()))
                        .findFirst()
                        .map(aluno -> clinicaService.criarSessao(paciente, aluno, request.getTipo()))
                )
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/{sessaoId}/evolucoes")
    public ResponseEntity<Evolucao> registrarEvolucao(@PathVariable Long sessaoId, @RequestBody EvolucaoRequest request) {
        return clinicaService.listarUsuarios().stream()
                .filter(u -> u.getId().equals(request.getAutorId()))
                .findFirst()
                .flatMap(autor -> clinicaService.registrarEvolucao(sessaoId, request.getTexto(), autor))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{sessaoId}/evolucoes")
    public List<Evolucao> listarEvolucoes(@PathVariable Long sessaoId) {
        return clinicaService.listarEvolucoesPorSessao(sessaoId);
    }

    @PostMapping("/{sessaoId}/assinar")
    public ResponseEntity<Sessao> assinarSessao(@PathVariable Long sessaoId, @RequestParam Long administradorId) {
        return clinicaService.listarUsuarios().stream()
                .filter(u -> u.getId().equals(administradorId) && u instanceof Administrador)
                .findFirst()
                .map(u -> (Administrador) u)
                .filter(administrador -> clinicaService.assinarSessao(sessaoId, administrador))
                .flatMap(administrador -> clinicaService.buscarSessaoPorId(sessaoId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
