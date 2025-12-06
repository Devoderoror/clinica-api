package br.com.clinica.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
@NoArgsConstructor
/**
 * Subclasse (Conceito de Herança)
 * Herda atributos e métodos de Usuario.
 * Representa o Administrador (Supervisor) do sistema.
 */
public class Administrador extends Usuario {

    public Administrador(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash);
    }

    @Override
        /**
     * Implementação do método abstrato (Conceito de Polimorfismo)
     * Define o papel específico para o Administrador.
     */
    @Override
    public String getPapel() {
        return "Administrador (Supervisor)";
    }
}
