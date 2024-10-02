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
    public Servico update(Servico _servico){
        Servico servicoEncontrado = servicoRepository.findAllById(_servico.getId_servicos());
        if (servicoEncontrado.getId_servicos() > 0)
            return  servicoRepository.save(servicoEncontrado);
        else
           return new Servico(0, "Servico não encontrado");

    }
    @Transactional
    public boolean delete(Servico _servico){
        boolean sucesso = false;
        Servico servicoEnncontrado = servicoRepository.findAllById(_servico.getId_servicos());
        if(servicoEnncontrado.getId_servicos() > 0){
            servicoRepository.deleteById(servicoEnncontrado.getId_servicos());
            sucesso = true;
        }
        return sucesso;
    }
}
