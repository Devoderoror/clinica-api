package br.com.clinica.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CLIENTE")
@NoArgsConstructor
/**
 * Subclasse (Conceito de Herança)
 * Herda atributos e métodos de Usuario.
 * Representa o Cliente (Estagiário) do sistema.
 */
public class Cliente extends Usuario {

    public Cliente(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash);
    }

    @Override
        /**
     * Implementação do método abstrato (Conceito de Polimorfismo)
     * Define o papel específico para o Cliente.
     */
    @Override
    public String getPapel() {
        return "Cliente (Estagiário)";
    }
}
