package br.com.clinica.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Evolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora = new Date();

    @Column(columnDefinition = "TEXT")
    private String texto;

    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private Usuario criadoPor;

    public Evolucao(Sessao sessao, String texto, Usuario criadoPor) {
        this.sessao = sessao;
        this.texto = texto;
        this.criadoPor = criadoPor;
    }
}
