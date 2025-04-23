package br.com.itb.miniprojetospring.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.itb.miniprojetospring.constants.MessageConstants;
import br.com.itb.miniprojetospring.Errors.InvalidDataException;
import br.com.itb.miniprojetospring.model.Senhas_Antigas;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.CriptografiaSenha;
import br.com.itb.miniprojetospring.service.EmailService;
import br.com.itb.miniprojetospring.service.Senhas_AntigasService;
import br.com.itb.miniprojetospring.service.UsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CriptografiaSenha criptografiaSenha;

    @Autowired
    private Senhas_AntigasService senhasAntigasService;

    private final MessageConstants messageConstants = new MessageConstants();

    // ==========================
    // 📩 Recuperação de Senha
    // ==========================

    @GetMapping("/validar-email-usuario")
    public ResponseEntity<String> validarEmailUsuario(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> {
                    usuarioService.gerarESalvarToken(usuario);
                    return ResponseEntity.ok(
                            emailService.enviarEmailRecuperacao(email, usuario.getnome_usuario(), usuario.getToken())
                    );
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(messageConstants.EMAIL_NOT_FOUND));
    }

    @PostMapping("/validar-token")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token)
            throws NoSuchAlgorithmException {

        Optional<Usuario> usuarioOpt = usuarioService.findByToken(token);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Token expirado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setToken(null);
        return ResponseEntity.ok("Token válido.");
    }

    @PutMapping("/{email_usuario}/senha")
public ResponseEntity<Object> atualizarSenha(
        @PathVariable String email_usuario,
        @RequestBody Map<String, String> requestBody) throws NoSuchAlgorithmException {

    String novaSenha = requestBody.get("novaSenha");

    if (novaSenha == null || novaSenha.isEmpty()) {
        return ResponseEntity.badRequest().body("A nova senha é obrigatória.");
    }

    Usuario usuario = usuarioService.findByEmailUsuario(email_usuario);
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }

    // Criptografa a nova senha
    String novaSenhaCriptografada = criptografiaSenha.criptografarSenha(novaSenha);

    // Verifica se a nova senha já foi usada anteriormente
    List<Senhas_Antigas> senhasAntigas = senhasAntigasService.findByCpf(usuario.getCpf());
    for (Senhas_Antigas senhaAntiga : senhasAntigas) {
        if (senhaAntiga.getSenha().equals(novaSenhaCriptografada)) {
            return ResponseEntity.badRequest().body("A nova senha não pode ser igual a uma senha antiga.");
        }
    }

    // Atualiza a senha e o token do usuário
    usuario.setSenha_usuario(novaSenhaCriptografada);
    usuario.setToken(null);

    // Cria novo registro de senha antiga
    Senhas_Antigas novaSenhaAntiga = new Senhas_Antigas(novaSenhaCriptografada, usuario);

    // Garante que a lista de senhas antigas está atualizada
    List<Senhas_Antigas> lista = usuario.getSenhasAntigas();
    if (lista == null) {    
        lista = new ArrayList<>();
    }
    lista.add(novaSenhaAntiga);
    if (usuario.getSenhasAntigas().size() > 3) {
        lista.remove(0); // Remove a senha mais antiga se houver mais de 3
    }
    usuario.setSenhasAntigas(lista);

    // Salva a nova senha antiga (pode ser redundante com o cascade, mas garante)
    senhasAntigasService.save(novaSenhaAntiga);

    // Atualiza o usuário no banco de dados
    usuarioService.update(usuario);

    return ResponseEntity.ok("Senha atualizada com sucesso.");
}


    // ==========================
    // 🧑‍💼 Criação de Usuário
    // ==========================

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) throws NoSuchAlgorithmException {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }
    @PostMapping("validar-usuario")
    public ResponseEntity<String> validarUsuario(@RequestBody Usuario usuario) {
        usuarioService.verificarDadosUsuario(usuario);
        return ResponseEntity.ok("ok");
    }

    // ==========================
    // 🔍 Consultas
    // ==========================

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmailUsuario(email);
        return usuario != null ?
                ResponseEntity.ok(usuario) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Integer> checkCpf(@PathVariable String cpf) {
        boolean exists = usuarioService.findByCpf(cpf).isPresent();
        return ResponseEntity.ok(exists ? 1 : 0);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmailUsuario(email);
        return ResponseEntity.ok(usuario != null);
    }

    // ==========================
    // ✏️ Atualização
    // ==========================

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

    // ==========================
    // ❌ Exclusão
    // ==========================

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String cpf) {
        try {
            usuarioService.deletarPorCpf(cpf);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o usuário.");
        }
    }
}
