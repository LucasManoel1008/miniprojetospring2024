package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Senhas_Antigas;
import br.com.itb.miniprojetospring.model.Senhas_AntigasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Senhas_AntigasService {
    private final Senhas_AntigasRepository senhasAntigasRepository;

    public Senhas_AntigasService(Senhas_AntigasRepository senhasAntigasRepository) {
        this.senhasAntigasRepository = senhasAntigasRepository;
    }

    public Senhas_Antigas save(Senhas_Antigas senhaAntiga) {
        return senhasAntigasRepository.save(senhaAntiga);
    }

    public List<Senhas_Antigas> findByCpf(String cpf) {
        return senhasAntigasRepository.findByUsuarioCpf(cpf);
    }
}
