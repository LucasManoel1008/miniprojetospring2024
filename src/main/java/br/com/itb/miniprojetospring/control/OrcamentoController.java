package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Orcamento;
import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.service.OrcamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3655, allowCredentials = "false")
@RequestMapping("/orcamento")
public class OrcamentoController {
    final OrcamentoService orcamentoService;
    public OrcamentoController(OrcamentoService _orcamentoService){
        this.orcamentoService = _orcamentoService;
    }
    @PostMapping
    public ResponseEntity<Object> saveOrcamento(@RequestBody Orcamento orcamento){
        return ResponseEntity.status(HttpStatus.CREATED).body(orcamentoService.save(orcamento));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Orcamento>> getALlOrcamento(){
        return ResponseEntity.status(HttpStatus.OK).body(orcamentoService.findAll());
    }
    @PutMapping
    public ResponseEntity<Object> updateOrcamento(@RequestBody Orcamento orcamento){
        return ResponseEntity.status(HttpStatus.CREATED).body(orcamentoService.update(orcamento));
    }

    @DeleteMapping
    public ResponseEntity<Object> deletarOrcamento(@RequestBody Orcamento orcamento){
        return ResponseEntity.status(HttpStatus.CREATED).body(orcamentoService.delete(orcamento));
    }
}
