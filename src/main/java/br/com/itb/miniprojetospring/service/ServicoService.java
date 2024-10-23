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
        // Log para verificar os dados do serviço que está sendo salvo
        System.out.println("Salvando serviço: " + _servico);
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
                    servicoEncontrado.setCategoria_servico(_servico.getCategoria_servico());
                    servicoEncontrado.setDisponibilidade_servico(_servico.getDisponibilidade_servico());
                    servicoEncontrado.setImagem_servico(_servico.getImagem_servico());
                    servicoEncontrado.setLocal_servico(_servico.getLocal_servico());
                    servicoEncontrado.setValor_estimado_servico(_servico.getValor_estimado_servico());
                    servicoEncontrado.setStatus_servico(_servico.getStatus_servico());
                    servicoEncontrado.setCriterios_servico(_servico.getCriterios_servico());
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
