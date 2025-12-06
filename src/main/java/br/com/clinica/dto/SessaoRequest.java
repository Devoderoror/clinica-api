package br.com.clinica.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SessaoRequest {
    private Long pacienteId;
    private Long alunoId;
    private String tipo;
}
