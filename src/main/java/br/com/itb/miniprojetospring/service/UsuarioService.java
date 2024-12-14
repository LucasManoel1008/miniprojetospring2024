package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    final UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;
    private static final int TOKEN_LENGTH = 9;

    public UsuarioService(UsuarioRepository _usuarioRepository){
        this.usuarioRepository = _usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario _usuario){
        return usuarioRepository.save(_usuario);
    }

    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Usuario findByEmailUsuario(String email_usuario){ // Buscar por e-mail
        return usuarioRepository.findByEmail(email_usuario).orElse(null);
    }

    // Esse é diferente do de cima, funciona apenas no token
    public Optional<Usuario> buscarPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario);
    }

    public void gerarESalvarToken(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String token = gerarToken();
        usuario.setToken(token);

        usuarioRepository.save(usuario);
    }

    private String gerarToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder();
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return token.toString();
    }
    public List<Usuario> findAll(){
        return  usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String cpf){
        return usuarioRepository.findById(cpf);
    }

    public void atualizarToken(String emailUsuario, String token, LocalDateTime expiracaoToken) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailUsuario);
        usuarioOpt.ifPresent(usuario -> {
            usuario.setToken(token);
            usuario.setExpiracao_token(expiracaoToken);
            usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> findByToken(String token){
        return usuarioRepository.findByToken(token);
    }


    public void deletarPorCpf(String cpf) {
        Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        }
    }

    public Usuario update(Usuario _usuario) {
        return usuarioRepository.findById(_usuario.getCpf())
                .map(usuarioEncontrado -> {
                    usuarioEncontrado.setNome_usuario(_usuario.getnome_usuario());
                    usuarioEncontrado.setSenha_usuario(_usuario.getSenha_usuario());
                    usuarioEncontrado.setEmail(_usuario.getEmail());
                    usuarioEncontrado.setCpf(_usuario.getCpf());
                    return usuarioRepository.save(usuarioEncontrado);
                })
                .orElse(null);
    }
    @Transactional
    public boolean delete(Usuario _usuario) {
        return usuarioRepository.findById(_usuario.getCpf())
                .map(usuarioEncontrado -> {
                    usuarioRepository.delete(usuarioEncontrado);
                    return true;
                })
                .orElse(false);
    }
}
