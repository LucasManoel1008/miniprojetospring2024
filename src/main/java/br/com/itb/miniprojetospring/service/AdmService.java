package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Adm;
import br.com.itb.miniprojetospring.model.AdmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdmService {

    @Autowired
    private AdmRepository admRepository;

    @Transactional
    public Adm save(Adm adm) {
        return admRepository.save(adm);
    }

}
