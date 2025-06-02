package br.com.itb.miniprojetospring.controller;

import br.com.itb.miniprojetospring.Errors.InvalidDataException;
import br.com.itb.miniprojetospring.model.Adm;
import br.com.itb.miniprojetospring.service.AdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private AdmService admService;

    @PostMapping("/login")
    public ResponseEntity<Adm> login(@RequestParam String nome_adm,@RequestParam String senha_adm ) {
        Adm adm = admService.findByName(nome_adm);
        if (adm == null) {
            throw new InvalidDataException("Dados incorretos", "Adm");
        }

        if(Objects.equals(senha_adm, adm.getSenha_adm()) && senha_adm.length() >= 8 && senha_adm != null ){
            return ResponseEntity.ok(adm);
        }
        throw new InvalidDataException("Dados incorretos", "Adm");
    }

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
