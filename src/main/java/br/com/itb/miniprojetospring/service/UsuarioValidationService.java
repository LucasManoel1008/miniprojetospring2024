package br.com.itb.miniprojetospring.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import br.com.itb.miniprojetospring.exceptions.DataAlredyRegistred;
import br.com.itb.miniprojetospring.exceptions.InvalidDataException;
import br.com.itb.miniprojetospring.model.InvalidData;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;

@Component
public class UsuarioValidationService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioValidationService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
    public void validarUsuarioParaCriacao(Usuario usuario) {
    	
    	if (usuario == null) {
			throw new InvalidDataException("Usuário não pode ser nulo");
		}
    	validarNomeUsuario(usuario.getnome_usuario());    	
    	validarSenhaUsuario(usuario.getSenha_usuario());
    	validarEmailUsuario(usuario.getEmail());
    	validarCpfUsuario(usuario.getCpf());
    	validarDataNascimentoUsuario(usuario.getData_nascimento());

    }
    
    public void validarNomeUsuario(String nome) {
		if (nome == null || nome.isEmpty()) {
			 throw new InvalidDataException("Insira um nome válido");
	}
}
    public void validarSenhaUsuario(String senha) {
		if (senha == null || senha.isEmpty()) {
			throw new InvalidDataException("Insira uma senha válida");
		}
		else if (senha.length() < 8) {
			throw new InvalidDataException("A senha deve ter no mínimo 8 caracteres");
		}
//		else if (!senha.matches(".*[a-zA-Z].*")) {
//			throw new InvalidDataException("A senha deve conter pelo menos uma letra");
//		}
//		else if (!senha.matches(".*\\d.*")) {
//			throw new InvalidDataException("A senha deve conter pelo menos um número");
//		}
//		else if (!senha.matches(".*[@#$%^&+=!].*")) {
//			throw new InvalidDataException("A senha deve conter pelo menos um caractere especial");
//		}
//		else if (senha.matches(".*\\s.*")) {
//			throw new InvalidDataException("A senha não pode conter espaços em branco");
//		}
//		else if (senha.matches(".*[<>\"'\\\\].*")) {
//			throw new InvalidDataException("A senha não pode conter caracteres inválidos");
//		}
//		else if (senha.matches(".*(123|abc|password|senha).*")) {
//			throw new InvalidDataException("A senha não pode conter sequências comuns");
//		}
//		else if (senha.matches(".*" + senha.substring(0, 3) + ".*")) {
//			throw new InvalidDataException("A senha não pode conter repetições de caracteres");
//		}
	}

	public void validarEmailUsuario(String email) {
		
		if (email == null || email.isEmpty()) {
			throw new InvalidDataException("Insira um e-mail válido");
		}
		else if (usuarioRepository.findByEmail(email).isPresent()) {
			throw new DataAlredyRegistred("E-mail já cadastrado");
		}
		else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			throw new InvalidDataException("Insira um e-mail válido");
		}
		else if (email.contains(" ")) {
			throw new InvalidDataException("O e-mail não pode conter espaços em branco");
		}
		else if (email.matches(".*[<>\"'\\\\].*")) {
			throw new InvalidDataException("O e-mail não pode conter caracteres inválidos");
		}
	}
	
	public void validarCpfUsuario(String cpf) {
		if (cpf == null || cpf.isEmpty()) {
			throw new InvalidDataException("Insira um CPF válido");
		}
		else if(usuarioRepository.findByCpf(cpf).isPresent()) {
    		throw new DataAlredyRegistred("CPF já cadastrado");
    	}
		else if (!cpf.matches("\\d{11}")) {
			throw new InvalidDataException("Insira um CPF válido");
		}
		else if (cpf.contains(" ")) {
			throw new InvalidDataException("O CPF não pode conter espaços em branco");
		}
		else if (cpf.matches(".*[<>\"'\\\\].*")) {
			throw new InvalidDataException("O CPF não pode conter caracteres inválidos");
		}
	}
	
	public void validarDataNascimentoUsuario(Date data_nascimento) {
		if (data_nascimento == null || data_nascimento.toInstant().
				atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.isAfter(LocalDateTime.now())) {
			throw new InvalidDataException("Data de nascimento inválida");
		}
	}
}

