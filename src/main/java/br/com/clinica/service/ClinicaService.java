package br.com.clinica.service;

import br.com.clinica.model.*;
import br.com.clinica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private EvolucaoRepository evolucaoRepository;

    public Optional<Usuario> autenticarUsuario(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.autenticar(senha));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Paciente cadastrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    public Sessao criarSessao(Paciente paciente, Usuario cliente, String tipo) {
        Sessao novaSessao = new Sessao(paciente, cliente, tipo);
        Sessao sessaoSalva = sessaoRepository.save(novaSessao);

        if (paciente.getStatus().equals("CADASTRADO")) {
            paciente.setStatus("EM_AVALIAÇÃO");
            pacienteRepository.save(paciente);
        }

        return sessaoSalva;
    }

    public Optional<Sessao> buscarSessaoPorId(Long id) {
        return sessaoRepository.findById(id);
    }

    @Transactional
    public Optional<Evolucao> registrarEvolucao(Long sessaoId, String texto, Usuario autor) {
        return buscarSessaoPorId(sessaoId).map(sessao -> {
            Evolucao novaEvolucao = new Evolucao(sessao, texto, autor);
            return evolucaoRepository.save(novaEvolucao);
        });
    }

    @Transactional
    public boolean assinarSessao(Long sessaoId, Administrador administrador) {
        return buscarSessaoPorId(sessaoId).map(sessao -> {
            boolean assinado = sessao.assinar(administrador);
            if (assinado) {
                sessaoRepository.save(sessao);
            }
            return assinado;
        }).orElse(false);
    }

    public List<Evolucao> listarEvolucoesPorSessao(Long sessaoId) {
        return evolucaoRepository.findBySessaoId(sessaoId);
    }

    @Transactional
    public void inicializarUsuarios() {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Administrador("Adm. Supervisor", "prof@clinica.com", "123"));
            usuarioRepository.save(new Cliente("Cliente Estagiário", "aluno@clinica.com", "123"));
        }
    }
}
