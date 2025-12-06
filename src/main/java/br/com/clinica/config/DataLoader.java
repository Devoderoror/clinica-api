package br.com.clinica.config;

import br.com.clinica.service.ClinicaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initDatabase(ClinicaService service) {
        return args -> {
            service.inicializarUsuarios();
            System.out.println("Usuários de teste inicializados.");
        };
    }
}
