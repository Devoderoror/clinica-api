package br.com.clinica.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    private String telefone;
    private String endereco;
    private String status = "CADASTRADO";

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlta;

    public Paciente(String nome, String cpf, Date dataNascimento, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = new Date();
    }
}
