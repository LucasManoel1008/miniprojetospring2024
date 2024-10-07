package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Orcamento;
import br.com.itb.miniprojetospring.model.OrcamentoRepository;
import br.com.itb.miniprojetospring.model.Servico;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrcamentoService {
    final OrcamentoRepository orcamentoRepository;

    public OrcamentoService(OrcamentoRepository _orcamentoRepository){
        this.orcamentoRepository = _orcamentoRepository;
    }

    @Transactional
    public Orcamento save(Orcamento _orcamento){
        return orcamentoRepository.save(_orcamento);
    }
    public List<Orcamento> findAll(){
        List<Orcamento> lista = orcamentoRepository.findAll();
        return lista;
    }
    public Orcamento findAllById(Long id){
        Orcamento orcamentoEncontrado = orcamentoRepository.findAllById(id);
        return orcamentoEncontrado;
    }
    @Transactional
    public Orcamento update(Orcamento _orcamento) {
        return orcamentoRepository.findById(_orcamento.getId())
                .map(servicoEncontrado -> {
                    servicoEncontrado.setValor_servico(_orcamento.getValor_servico());
                    return orcamentoRepository.save(servicoEncontrado);
                })
                .orElse(null);
    }
    @Transactional
    public boolean delete(Orcamento _orcamento){
        return orcamentoRepository.findById(_orcamento.getId())
                .map(orcamentoEncontrado ->{
            orcamentoRepository.delete(orcamentoEncontrado);
            return true;
        })
                .orElse(false);
    }

}
