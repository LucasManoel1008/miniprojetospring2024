package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.EmpresaRepository;
import br.com.itb.miniprojetospring.model.Servico;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository _empresaRepository){
        this.empresaRepository = _empresaRepository;
    }

    @Transactional
    public Empresa save(Empresa _empresa){
        return empresaRepository.save(_empresa);
    }
    public List<Empresa> findAll(){
        List<Empresa> lista = empresaRepository.findAll();
        return lista;
    }
    public Empresa findAllById(long id){
        Empresa empresaEncontrada = empresaRepository.findAllById(id);
        return empresaEncontrada;
    }
    @Transactional
    public Empresa update(Empresa _empresa) {
        return empresaRepository.findById(_empresa.getId())
                .map(empresaEncontrda -> {
                    empresaEncontrda.setNome_empresa(_empresa.getNome_empresa());
                    empresaEncontrda.setDescricao_empresa(_empresa.getDescricao_empresa());
                    empresaEncontrda.setRua(_empresa.getRua());
                    empresaEncontrda.setNumero(_empresa.getNumero());
                    empresaEncontrda.setBairro(_empresa.getBairro());
                    empresaEncontrda.setCidade(_empresa.getCidade());
                    empresaEncontrda.setCep(_empresa.getCep());
                    return empresaRepository.save(empresaEncontrda);
                })
                .orElse(null);
    }
    @Transactional
    public boolean delete(Empresa _empresa) {
        return empresaRepository.findById(_empresa.getId())
                .map(empresaEncontrado -> {
                    empresaRepository.delete(empresaEncontrado);
                    return true;
                })
                .orElse(false);
    }

}
