package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsuarioService {
    final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository _usuarioRepository){
        this.usuarioRepository = _usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario _usuario){
        return usuarioRepository.save(_usuario);
    }

    public List<Usuario> findAll(){
        List<Usuario> lista = usuarioRepository.findAll();
        return lista;
    }
    public Usuario findAllById(long id){
        Usuario usuarioEncontrado = usuarioRepository.findAllById(id);
        return usuarioEncontrado;
    }
    public Usuario update(Usuario _usuario) {
        return usuarioRepository.findById(_usuario.getId())
                .map(usuarioEncontrado -> {
                    usuarioEncontrado.setNome(_usuario.getNome());
                    usuarioEncontrado.setUltimonome_usuario(_usuario.getUltimonome_usuario());
                    usuarioEncontrado.setSenha_usuario(_usuario.getSenha_usuario());
                    usuarioEncontrado.setEmail_usuario(_usuario.getEmail_usuario());
                    usuarioEncontrado.setCPF(_usuario.getCPF());
                    return usuarioRepository.save(usuarioEncontrado);
                })
                .orElse(null);
    }
    @Transactional
    public boolean delete(Usuario _usuario) {
        return usuarioRepository.findById(_usuario.getId())
                .map(usuarioEncontrado -> {
                    usuarioRepository.delete(usuarioEncontrado);
                    return true;
                })
                .orElse(false);
    }
}
