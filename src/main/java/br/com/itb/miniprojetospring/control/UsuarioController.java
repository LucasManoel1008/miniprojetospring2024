package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import br.com.itb.miniprojetospring.service.EmailService;
import br.com.itb.miniprojetospring.service.EmailServiceImpl;
import br.com.itb.miniprojetospring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    final UsuarioService usuarioService;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    public UsuarioController(UsuarioService _usuarioService) {
        this.usuarioService = _usuarioService;
    }
    // testa o email do usuario

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody Map<String, String> payload) {
        String emailUsuario = payload.get("email_usuario");
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(emailUsuario);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get(); // Desembrulha o Optional

            LocalDateTime expiracao = LocalDateTime.now().plusMinutes(10); // Define 30 minutos de validade

            usuarioService.gerarESalvarToken(emailUsuario);
            String token = usuario.getToken();
            emailService.enviarEmailRecuperacao(emailUsuario, token);

            return ResponseEntity.ok("E-mail de recuperação enviado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
        }
    }

    // Realizar a validação do Token
    @PostMapping("/validar-token")
    public ResponseEntity<Map<String, Object>> validarToken(@RequestParam String token) {
        Map<String, Object> response = new HashMap<>();
        boolean tokenValido = emailServiceImpl.validarToken(token);

        if (tokenValido) {
            response.put("tokenValido", true);
        } else {
            response.put("tokenValido", false);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestParam String novaSenha){
        Optional<Usuario> usuarioOpt = usuarioService.findByToken(token);

        if (usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();
            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(usuario.getExpiracao_token())){
                usuario.setSenha_usuario(novaSenha); // Atualiza a senha
                usuario.setSenhaToken(null);
                usuario.setToken(null);
                usuarioService.save(usuario);
                return ResponseEntity.ok("Senha redefinida com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("Token expirado.");
            }
        } else {
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        System.out.println(System.currentTimeMillis());
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
            usuario.setCpf(cpf);
            return ResponseEntity.ok(usuarioService.update(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
    @PutMapping("/{email_usuario}/senha")
    public ResponseEntity<Object> atualizarSenha(@PathVariable String email_usuario, @RequestBody Map<String, String> requestBody) {
        // Extrair a nova senha do corpo da requisição
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
