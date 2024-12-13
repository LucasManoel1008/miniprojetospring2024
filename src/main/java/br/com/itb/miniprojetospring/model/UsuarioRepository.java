package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository 
	extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByCpf(String cpf);
	Optional<Usuario> findByEmail(String email_usuario);
}
