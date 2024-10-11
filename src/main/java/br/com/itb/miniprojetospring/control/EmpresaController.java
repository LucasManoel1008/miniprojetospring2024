package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.EmpresaService;
import br.com.itb.miniprojetospring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    final EmpresaService empresaService;

    @Autowired
    private UsuarioService usuarioService;

    public EmpresaController(EmpresaService _empresaService) {
        this.empresaService = _empresaService;
    }

    // Método para criar uma nova empresa
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa, @RequestParam Long cpfUsuario) {
        Usuario usuario = usuarioService.findByCpf(cpfUsuario);
        if (usuario != null) {
            empresa.setUsuario(usuario);
            empresaService.save(empresa);
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    // Método para obter uma empresa pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(id);
        if (empresaOptional.isPresent()) {
            return ResponseEntity.ok(empresaOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para obter todas as empresas
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findAll());
    }

    @PutMapping
    public ResponseEntity<Object> updateEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.update(empresa));
    }

    @DeleteMapping
    public ResponseEntity<Object> deletarEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.delete(empresa));
    }
}
