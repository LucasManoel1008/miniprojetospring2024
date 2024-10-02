package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository
    extends JpaRepository<Servico, Long> {
    Servico findAllById(Long id_servico);
}
