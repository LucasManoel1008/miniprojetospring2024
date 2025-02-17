package br.com.itb.miniprojetospring.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.model.ServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class ServicoService {
    final ServicoRepository servicoRepository;
    public List<Servico> findAllByEmpresa(Empresa empresa) {
        return servicoRepository.findAllByEmpresa(empresa);
    }
    public ServicoService(ServicoRepository _servicoRepository) {
        this.servicoRepository = _servicoRepository;
    }

    @Transactional
    public Servico save(Servico _servico) {
        return servicoRepository.save(_servico);
    }
    public List<Servico> findByEmpresa(Empresa empresa) {
        return servicoRepository.findByEmpresa(empresa);
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

   public String calcularTempoPassado(LocalDateTime dataDisponibilidade, LocalDateTime dataAtual) {
    long segundosPassados = Duration.between(dataDisponibilidade, dataAtual).getSeconds();

    if (segundosPassados < 0) {
        return "Estará disponível em: " + dataDisponibilidade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    } else if (segundosPassados < 60) {
        return segundosPassados < 10 ? "Agora mesmo" : "Há " + segundosPassados + " segundos";
    } else if (segundosPassados < 3600) {
        long minutos = segundosPassados / 60;
        return minutos > 1 ? "Há " + minutos + " minutos" : "Há " + minutos + " minuto";
    } else if (segundosPassados < 86400) {
        long horas = segundosPassados / 3600;
        return horas > 1 ? "Há " + horas + " horas" : "Há " + horas + " hora";
    } else if (segundosPassados < 2592000) {
        long dias = segundosPassados / 86400;
        return dias > 1 ? "Há " + dias + " dias" : "Há " + dias + " dia";
    } else if (segundosPassados < 31536000) {
        long meses = segundosPassados / 2592000;
        return meses > 1 ? "Há " + meses + " meses" : "Há " + meses + " mês";
    } else {
        long anos = segundosPassados / 31536000;
        return anos > 1 ? "Há " + anos + " anos" : "Há " + anos + " ano";
    }
}

    public List<Servico> ordenarServico(List<Servico> servicos, String data) {
        if (servicos.size() <=1){
            return servicos;
        }
        else if (data.equals("todos")){
            return servicos;
        }
        LocalDateTime dataAtual = LocalDateTime.now();
        List<Servico> servicosFormatados = new ArrayList<>();
        for (Servico servico : servicos){
            LocalDateTime dataComparada = servico.getDisponibilidade_servico().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (dataComparada.isBefore(dataAtual) || dataComparada.isEqual(dataAtual)){
                servicosFormatados.add(servico);
            }
        }
        if (data.equals("antigo")){
            quickSort(servicosFormatados);
        }
         else if (data.equals("recente")) {
            quickSort(servicosFormatados);
            Collections.reverse(servicosFormatados);
        }
        System.out.println("Lista organizada: " + Arrays.toString(servicosFormatados.toArray()));
            return servicosFormatados;

    }

    public List<String> quickSort(List<Servico> servicos) {
        if (servicos.size() <= 1) {
            return new ArrayList<>();
        }
        List<Servico> menores = new ArrayList<>();
        List<Servico> maiores = new ArrayList<>();
        Servico pivo = servicos.get(0);
        for (int i = 1; i < servicos.size(); i++) {
            if (servicos.get(i).getDisponibilidade_servico().before(pivo.getDisponibilidade_servico())) {
                menores.add(servicos.get(i));
            } else {
                maiores.add(servicos.get(i));
            }
        }
        List<String> lista = new ArrayList<>();
        lista.addAll(quickSort(menores));
        lista.add(pivo.getNome_servico());
        lista.addAll(quickSort(maiores));
        return lista;
    }

}

