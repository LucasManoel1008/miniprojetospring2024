package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository _usuarioRepository){
        this.usuarioRepository = _usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario _usuario){
        return usuarioRepository.save(_usuario);
    }
    public Usuario findByCpf(Long cpf) {
        return usuarioRepository.findByCpf(cpf);
    }
    public List<Usuario> findAll(){
        return  usuarioRepository.findAll();
    }
    public Optional<Usuario> findById(Long cpf){
        return usuarioRepository.findById(cpf);
    }

    public Usuario update(Usuario _usuario) {
        return usuarioRepository.findById(_usuario.getCpf())
                .map(usuarioEncontrado -> {
                    usuarioEncontrado.setNome_usuario(_usuario.getnome_usuario());
                    usuarioEncontrado.setSenha_usuario(_usuario.getSenha_usuario());
                    usuarioEncontrado.setEmail_usuario(_usuario.getEmail_usuario());
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
