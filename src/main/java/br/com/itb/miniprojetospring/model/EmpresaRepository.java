package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> { // Alterado para String
	Optional<Empresa> findByCnpj(String cnpj); // Renomeado para refletir que busca por CNPJ
}
