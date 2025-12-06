package br.com.clinica.controller;

import br.com.clinica.dto.AuthRequest;
import br.com.clinica.model.Usuario;
import br.com.clinica.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticar(@RequestBody AuthRequest request) {
        return clinicaService.autenticarUsuario(request.getEmail(), request.getSenha())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return clinicaService.listarUsuarios();
    }
}
