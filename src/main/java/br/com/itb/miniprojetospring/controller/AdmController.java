package br.com.itb.miniprojetospring.controller;

import br.com.itb.miniprojetospring.Errors.InvalidDataException;
import br.com.itb.miniprojetospring.model.Adm;
import br.com.itb.miniprojetospring.service.AdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private AdmService admService;

    @PostMapping("/cadastro-adm")
    public ResponseEntity<Adm> cadastrarAdm(@RequestBody Adm adm) {
        if(adm.getNome().equals("")){
            throw new InvalidDataException("Dados vazios não são válidos", "NOME");
        }
        if(adm.getSenha_adm().equals("") || adm.getSenha_adm().length() < 8){
            throw new InvalidDataException("Senha inválida", "SENHA");
        }
        return ResponseEntity.ok(admService.save(adm));
    }
}
