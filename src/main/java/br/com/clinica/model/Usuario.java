package br.com.clinica.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
/**
 * Classe Abstrata (Conceito de Herança)
 * Representa a superclasse de todos os usuários do sistema (Administrador e Cliente).
 * Define atributos e comportamentos comuns, como autenticação.
 */
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senhaHash;

    private boolean ativo = true;

    public Usuario(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.ativo = true;
    }

    @Transient
        /**
     * Método Abstrato (Conceito de Polimorfismo)
     * Força as subclasses (Administrador e Cliente) a implementarem sua própria
     * definição de "papel" no sistema.
     */
    public abstract String getPapel();

        /**
     * Método de Autenticação (Conceito de Encapsulamento)
     * O acesso à senha real (senhaHash) é encapsulado dentro da classe.
     * A lógica de comparação é exposta apenas através deste método público.
     */
    public boolean autenticar(String senha) {
        return this.senhaHash.equals(senha);
    }
}
