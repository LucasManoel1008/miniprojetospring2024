package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface Senhas_AntigasRepository extends JpaRepository<Senhas_Antigas, UUID> {
    List<Senhas_Antigas> findByUsuarioCpf(String cpf);
}
