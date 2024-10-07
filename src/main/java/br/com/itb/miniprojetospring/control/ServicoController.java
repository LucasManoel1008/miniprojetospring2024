package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Long id) {
        Optional<Servico> servicoOptional = servicoService.findById(id);
        if (servicoOptional.isPresent()) {
            return ResponseEntity.ok(servicoOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        return ResponseEntity.ok(servicoService.findAll());
    }

    @PutMapping
    public ResponseEntity<Object> updateServico(@RequestBody Servico servico){
        return ResponseEntity.ok(servicoService.update(servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteServico(@PathVariable Long id){
        servicoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
