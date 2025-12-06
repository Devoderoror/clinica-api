package br.com.clinica.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthRequest {
    private String email;
    private String senha;
}
