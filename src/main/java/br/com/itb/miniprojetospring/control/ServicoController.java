package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/servico")
public class ServicoController {
    final ServicoService servicoService;

    public ServicoController(ServicoService _serviceService){
        this.servicoService = _serviceService;
    }
    @PostMapping
    public ResponseEntity<Object> saveService(@RequestBody Servico servico){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.save(servico));
    }
    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos(){
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.findAll());
    }
    @PutMapping
    public ResponseEntity<Object> updateServico(@RequestBody Servico servico){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.update(servico));
    }
    @DeleteMapping
    public ResponseEntity<Object> deleteServico(@RequestBody Servico servico){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(servicoService.update(servico));
    }
}
