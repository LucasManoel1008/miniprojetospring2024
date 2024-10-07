package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.model.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository _servicoRepository) {
        this.servicoRepository = _servicoRepository;
    }

    @Transactional
    public Servico save(Servico _servico) {
        return servicoRepository.save(_servico);
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> findById(Long id) {
        return servicoRepository.findById(id);
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
    public boolean delete(Long id) {
        return servicoRepository.findById(id)
                .map(servicoEncontrado -> {
                    servicoRepository.delete(servicoEncontrado);
                    return true;
                })
                .orElse(false);
    }
}
