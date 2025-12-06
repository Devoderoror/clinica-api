package br.com.clinica.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
public class PacienteRequest {
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String telefone;
    private String endereco;
}
