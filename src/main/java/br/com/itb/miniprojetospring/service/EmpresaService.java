package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
	
	@Autowired
    private EmpresaRepository empresaRepository;
	
	@Autowired
    private EmpresaValidationService empresaValidationService;

    // =====================================
    // 📦 PERSISTÊNCIA (CREATE / UPDATE)
    // =====================================

    @Transactional
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
    
    @Transactional
    public void verificarDadosEmpresa(Empresa empresa) {
    	empresaValidationService.validarEmpresaParaCriacao(empresa);
    }

    @Transactional
    public Empresa update(Empresa empresa) {
        return empresaRepository.findByCnpj(empresa.getCnpj())
                .map(empresaEncontrada -> {
                    empresaEncontrada.setNome_empresa(empresa.getNome_empresa());
                    empresaEncontrada.setDescricao_empresa(empresa.getDescricao_empresa());
                    empresaEncontrada.setRua(empresa.getRua());
                    empresaEncontrada.setNumero(empresa.getNumero());
                    empresaEncontrada.setBairro(empresa.getBairro());
                    empresaEncontrada.setCidade(empresa.getCidade());
                    empresaEncontrada.setCep(empresa.getCep());
                    empresaEncontrada.setTelefone_empresa(empresa.getTelefone_empresa());
                    return empresaRepository.save(empresaEncontrada);
                })
                .orElse(null);
    }

    // =====================================
    // 🔍 CONSULTAS (READ)
    // =====================================

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findByCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj);
    }

    public Optional<Empresa> findAllById(String cnpj) {
        return empresaRepository.findByCnpj(cnpj);
    }

    // =====================================
    // ❌ EXCLUSÕES (DELETE)
    // =====================================

    public void deletarPorCnpj(String cnpj) {
        empresaRepository.findByCnpj(cnpj).ifPresent(empresaRepository::delete);
    }

    @Transactional
    public boolean delete(Empresa empresa) {
        return empresaRepository.findByCnpj(empresa.getCnpj())
                .map(empresaEncontrada -> {
                    empresaRepository.delete(empresaEncontrada);
                    return true;
                })
                .orElse(false);
    }
}
