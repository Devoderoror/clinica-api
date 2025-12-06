package br.com.clinica.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EvolucaoRequest {
    private String texto;
    private Long autorId;
}
