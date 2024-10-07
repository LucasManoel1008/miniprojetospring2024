package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/empresa")
public class EmpresaController {
    final EmpresaService empresaService;

    public EmpresaController(EmpresaService _empresaService){
        this.empresaService = _empresaService;
    }
    @PostMapping
    public ResponseEntity<Object> saveService(@RequestBody Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(empresa));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Empresa>> getAllEmpresa(){
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findAll());
    }
    @PutMapping
    public ResponseEntity<Object> updateEmpresa(@RequestBody Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.update(empresa));
    }
    @DeleteMapping
    public ResponseEntity<Object> deletarEmpresa(@RequestBody Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.delete(empresa));
    }
}
