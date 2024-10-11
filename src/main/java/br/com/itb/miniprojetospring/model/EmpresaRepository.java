package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository 
	extends JpaRepository<Empresa, Long> {
	Empresa findAllById(long id);

}
