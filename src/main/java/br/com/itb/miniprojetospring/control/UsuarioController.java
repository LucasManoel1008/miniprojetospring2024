package br.com.itb.miniprojetospring.control;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itb.miniprojetospring.constants.MessageConstants;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.EmailService;
import br.com.itb.miniprojetospring.service.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/usuario")
public class UsuarioController {

    private MessageConstants messageConstants;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmailService emailService;


    public UsuarioController(UsuarioService _usuarioService) {
        this.usuarioService = _usuarioService;
    }

    // removi o emailService.enviarEmaildeRecuperacao
    @GetMapping("/validar-email-usuario")
    public ResponseEntity<String> validarEmailUsuario(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> {
                  usuarioService.gerarESalvarToken(usuario);
                  return ResponseEntity.ok(emailService.enviarEmailRecuperacao(email, usuario.getnome_usuario(), usuario.getToken()));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageConstants.EMAIL_NOT_FOUND));
    }
    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestParam String novaSenha) throws NoSuchAlgorithmException{
        Optional<Usuario> usuarioOpt = usuarioService.findByToken(token);

        if (usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();
            LocalDateTime now = LocalDateTime.now();
            usuario.setSenha_usuario(novaSenha); // Atualiza a senha
            usuario.setSenhaToken(null);
            usuario.setToken(null);
            usuarioService.save(usuario);
            return ResponseEntity.ok("Senha redefinida com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("Token expirado.");
            }
        }
        
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmailUsuario(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/{cpf}")
    public ResponseEntity<Integer> checkCpf(@PathVariable String cpf){
        Usuario usuario = usuarioService.findByCpf(cpf).orElse(null);
        if(usuario != null){
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }
    @GetMapping("check-email/{email}")
    public ResponseEntity<Integer> checkEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmailUsuario(email);
        if (usuario != null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
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
            usuario.setCpf(cpf);
            return ResponseEntity.ok(usuarioService.update(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    @PutMapping("/{email_usuario}/senha")
    public ResponseEntity<Object> atualizarSenha(@PathVariable String email_usuario, @RequestBody Map<String, String> requestBody) {
        String novaSenha = requestBody.get("novaSenha");

        if (novaSenha == null || novaSenha.isEmpty()) {
            return ResponseEntity.badRequest().body("A nova senha é obrigatória.");
        }

        // Buscar o usuário pelo e-mail
        Usuario usuario = usuarioService.findByEmailUsuario(email_usuario);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        // Atualizar a senha e salvar no banco de dados
        usuario.setSenha_usuario(novaSenha);
        usuario.setToken(null);
        usuarioService.update(usuario); // Método deve salvar a alteração no banco

        return ResponseEntity.ok("Senha atualizada com sucesso.");
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
