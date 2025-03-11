package br.com.itb.miniprojetospring.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itb.miniprojetospring.constants.TokenConstants;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    final UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private CriptografiaSenha criptografiaSenha;

    TokenConstants tokenConstants;

    public UsuarioService(UsuarioRepository _usuarioRepository){
        this.usuarioRepository = _usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario _usuario) throws NoSuchAlgorithmException{
    	String senhaCriptografada = criptografiaSenha.criptografarSenha(_usuario.getSenha_usuario());
        _usuario.setSenha_usuario(senhaCriptografada);
        return usuarioRepository.save(_usuario);
    }

    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Usuario findByEmailUsuario(String email_usuario){ // Buscar por e-mail
        return usuarioRepository.findByEmail(email_usuario).orElse(null);
    }

    // BLOCK - Métodos para redefinir senha
    public Optional<Usuario> buscarPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario);
    }

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
        tokenService.adicionarToken(String.valueOf(tokenStringBuilder));
        return tokenStringBuilder.toString();
    }
    public List<Usuario> findAll(){
        return  usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(String cpf){
        return usuarioRepository.findById(cpf);
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
