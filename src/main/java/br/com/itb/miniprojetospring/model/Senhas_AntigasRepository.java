package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Senhas_AntigasRepository extends JpaRepository<Senhas_Antigas, Long> {
    List<Senhas_Antigas> findByUsuarioCpf(String cpf);
}
