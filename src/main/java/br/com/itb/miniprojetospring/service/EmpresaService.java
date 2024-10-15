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
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findAllById(String cnpj) {
        return empresaRepository.findByCnpj(cnpj); // Método simplificado
    }

    public void deletarPorCnpj(String cnpj) {
        Optional<Empresa> empresa = empresaRepository.findByCnpj(cnpj);
        if (empresa.isPresent()) {
            empresaRepository.delete(empresa.get());
        }
    }

    @Transactional
    public Empresa update(Empresa empresa) {
        return empresaRepository.findById(empresa.getCnpj())
                .map(empresaEncontrada -> {
                    empresaEncontrada.setNome_empresa(empresa.getNome_empresa());
                    empresaEncontrada.setDescricao_empresa(empresa.getDescricao_empresa());
                    empresaEncontrada.setRua(empresa.getRua());
                    empresaEncontrada.setNumero(empresa.getNumero());
                    empresaEncontrada.setBairro(empresa.getBairro());
                    empresaEncontrada.setCidade(empresa.getCidade());
                    empresaEncontrada.setCep(empresa.getCep());
                    return empresaRepository.save(empresaEncontrada);
                })
                .orElse(null);
    }

    @Transactional
    public boolean delete(Empresa empresa) {
        return empresaRepository.findById(empresa.getCnpj())
                .map(empresaEncontrada -> {
                    empresaRepository.delete(empresaEncontrada);
                    return true;
                })
                .orElse(false);
    }
}
