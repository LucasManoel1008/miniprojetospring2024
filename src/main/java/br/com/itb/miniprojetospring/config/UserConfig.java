package br.com.itb.miniprojetospring.config;

import br.com.itb.miniprojetospring.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public Usuario usuario() {
        return new Usuario(); // Cria e retorna uma instância básica do Usuário
    }
}
