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
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private final EmpresaService empresaService;

    @Autowired
    private final UsuarioService usuarioService;

    public EmpresaController(EmpresaService empresaService, UsuarioService usuarioService) {
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    // Método para criar uma nova empresa
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa, @RequestParam String cpfUsuario) {
        Optional<Usuario> usuarioOptional = usuarioService.findByCpf(cpfUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            empresa.setUsuario(usuario);
            empresaService.save(empresa);
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Método para obter uma empresa pelo CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable String cnpj) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(cnpj);
        return empresaOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método para obter todas as empresas
    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<Object> updateEmpresa(@PathVariable String cnpj, @RequestBody Empresa empresa) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(cnpj);
        if (empresaOptional.isPresent()) {
            empresa.setCnpj(cnpj); // Certifique-se de que o CNPJ está setado
            Empresa updatedEmpresa = empresaService.update(empresa);
            return ResponseEntity.ok(updatedEmpresa);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<String> deletarEmpresa(@PathVariable String cnpj) {
        try {
            empresaService.deletarPorCnpj(cnpj);
            return ResponseEntity.ok("Empresa deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar empresa");
        }
    }
}
