package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findById(Long id);
    List<Servico> findAllByEmpresa(Empresa empresa);
    List<Servico> findByEmpresa(Empresa empresa);

}

