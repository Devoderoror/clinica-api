package br.com.clinica.controller;

import br.com.clinica.dto.PacienteRequest;
import br.com.clinica.model.Paciente;
import br.com.clinica.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody PacienteRequest request) {
        Paciente paciente = new Paciente(
                request.getNome(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getTelefone(),
                request.getEndereco()
        );
        return ResponseEntity.ok(clinicaService.cadastrarPaciente(paciente));
    }

    @GetMapping
    public List<Paciente> listarTodos() {
        return clinicaService.listarPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return clinicaService.buscarPacientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
