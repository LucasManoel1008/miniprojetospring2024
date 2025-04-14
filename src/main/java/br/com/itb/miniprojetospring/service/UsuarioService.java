package br.com.itb.miniprojetospring.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import br.com.itb.miniprojetospring.constants.TokenConstants;
import br.com.itb.miniprojetospring.exceptions.DataAlredyRegistred;
import br.com.itb.miniprojetospring.model.Senhas_Antigas;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CriptografiaSenha criptografiaSenha;

    @Autowired
    private Senhas_AntigasService senhasAntigasService;

    @Autowired
    private UsuarioValidationService usuarioValidationService;

    TokenConstants tokenConstants;

    // ==========================
    // 📩 Recuperação de Senha
    // ==========================

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public void gerarESalvarToken(Usuario usuario) {
        usuario.setToken(gerarToken());
        usuarioRepository.save(usuario);
    }

    private String gerarToken() {
        SecureRandom secureNumberGenerator = new SecureRandom();
        StringBuilder tokenStringBuilder = new StringBuilder();
        for (int i = 0; i < TokenConstants.TOKEN_LENGTH; i++) {
            tokenStringBuilder.append(TokenConstants.TOKEN_CARACTERES.charAt(secureNumberGenerator.nextInt(TokenConstants.TOKEN_CARACTERES.length())));
        }
        tokenService.adicionarToken(tokenStringBuilder.toString());
        return tokenStringBuilder.toString();
    }

    // ==========================
    // 🧑‍💼 Criação de Usuário
    // ==========================

    @Transactional
    public Usuario save(Usuario usuario) throws NoSuchAlgorithmException {
    	
        usuarioValidationService.validarUsuarioParaCriacao(usuario);
        
        String senhaCriptografada = criptografiaSenha.criptografarSenha(usuario.getSenha_usuario());
        
        usuario.setSenha_usuario(senhaCriptografada);
        
        Senhas_Antigas senhaAntiga = new Senhas_Antigas(senhaCriptografada, usuario);
        
        usuario.setSenhasAntigas(List.of(senhaAntiga));

        return usuarioRepository.save(usuario);
    }

    // ==========================
    // 🔍 Consultas
    // ==========================

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Usuario findByEmailUsuario(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario).orElse(null);
    }

    public Optional<Usuario> findById(String cpf) {
        return usuarioRepository.findById(cpf);
    }

    public Optional<Usuario> findByToken(String token) {
        return usuarioRepository.findByToken(token);
    }

    // ==========================
    // ✏️ Atualização
    // ==========================

    @Transactional
    public Usuario update(Usuario usuario) {
        return usuarioRepository.findById(usuario.getCpf())
                .map(usuarioEncontrado -> {
                    usuarioEncontrado.setNome_usuario(usuario.getnome_usuario());
                    try {
                        usuarioEncontrado.setSenha_usuario(criptografiaSenha.criptografarSenha(usuario.getSenha_usuario()));
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    usuarioEncontrado.setEmail(usuario.getEmail());
                    usuarioEncontrado.setCpf(usuario.getCpf());
                    return usuarioRepository.save(usuarioEncontrado);
                })
                .orElse(null);
    }

    // ==========================
    // ❌ Exclusão
    // ==========================

    @Transactional
    public void deletarPorCpf(String cpf) {
        Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);
        usuario.ifPresent(usuarioRepository::delete);
    }
}
