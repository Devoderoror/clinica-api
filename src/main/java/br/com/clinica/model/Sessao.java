package br.com.clinica.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "cliente_responsavel_id")
    private Usuario clienteResponsavel;

    @ManyToOne
    @JoinColumn(name = "administrador_supervisor_id", nullable = true)
    private Administrador administradorSupervisor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora = new Date();

    private String tipo;
    private String observacoes;
    private boolean assinadaPorAdministrador = false;

    public Sessao(Paciente paciente, Usuario clienteResponsavel, String tipo) {
        this.paciente = paciente;
        this.clienteResponsavel = clienteResponsavel;
        this.tipo = tipo;
    }

    public boolean assinar(Administrador administrador) {
        if (administrador != null) {
            this.administradorSupervisor = administrador;
            this.assinadaPorAdministrador = true;
            return true;
        }
        return false;
    }
}
