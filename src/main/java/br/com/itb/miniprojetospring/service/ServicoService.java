package br.com.itb.miniprojetospring.service;


import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.model.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository _servicoRepository){
        this.servicoRepository = _servicoRepository;
    }

    @Transactional
    public Servico save(Servico _servico){
        return servicoRepository.save(_servico);
    }

    public List<Servico> findAll(){
        List<Servico> lista = servicoRepository.findAll();
        return  lista;
    }
    public Servico findAllById(long id){
        Servico servicoEncontrado = servicoRepository.findAllById(id);
        return servicoEncontrado;
    }
    @Transactional
    public Servico update(Servico _servico) {
        return servicoRepository.findById(_servico.getId())
                .map(servicoEncontrado -> {
                    servicoEncontrado.setNome_servico(_servico.getNome_servico());
                    servicoEncontrado.setDescricao_servico(_servico.getDescricao_servico());
                    return servicoRepository.save(servicoEncontrado);
                })
                .orElse(null);
    }

    @Transactional
    public boolean delete(Servico _servico) {
        return servicoRepository.findById(_servico.getId())
                .map(servicoEncontrado -> {
                    servicoRepository.delete(servicoEncontrado);
                    return true;
                })
                .orElse(false);
    }
}
