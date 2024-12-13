package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService _usuarioService) {
        this.usuarioService = _usuarioService;
    }


    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }
    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email_usuario){
        Usuario usuario = usuarioService.findByEmailUsuario(email_usuario);
        if (usuario !=null){
            return ResponseEntity.ok(usuario);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String cpf) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(cpf);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Object> updateUsuario(@PathVariable String cpf, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(cpf);
        if (usuarioOptional.isPresent()) {
            usuario.setCpf(cpf); // Certifique-se de que o CPF no objeto é o correto.
            return ResponseEntity.ok(usuarioService.update(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarEmpresa(@PathVariable String cpf) {
        try {
            usuarioService.deletarPorCpf(cpf);
            return ResponseEntity.ok("Usuario deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o usuaio");
        }
    }
}
