package br.com.itb.miniprojetospring.service;

import org.springframework.stereotype.Service;

import br.com.itb.miniprojetospring.Errors.DataAlredyRegistred;
import br.com.itb.miniprojetospring.Errors.InvalidDataException;
import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.EmpresaRepository;
import br.com.itb.miniprojetospring.model.UsuarioRepository;

@Service
public class EmpresaValidationService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    
    public EmpresaValidationService(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
		this.empresaRepository = empresaRepository;
		this.usuarioRepository = usuarioRepository;
	}
    
    public void validarEmpresaParaCriacao(Empresa empresa) {
		if (empresa == null) {
			throw new InvalidDataException("Empresa não pode ser nula", "empresa");
		}
		if (empresa.getUsuario() == null) {
			throw new InvalidDataException("Falha ao crirar o usuario", "usuario");
		}
		validarUsuarioParaEmpresa(empresa.getUsuario().getCpf());
		validarCnpj(empresa.getCnpj());
		validarNomeEmpresa(empresa.getNome_empresa());
		
		validarDescricaoEmpresa(empresa.getDescricao_empresa());
		validarTelefoneEmpresa(empresa.getTelefone_empresa());
		validarCepEmpresa(empresa.getCep());
		
    }
    
    public void validarUsuarioParaEmpresa(String cpf) {
    	if(!usuarioRepository.findByCpf(cpf).isPresent()) {
    		throw new InvalidDataException("Falha ao criar o usuario", "usuario");
    	}
    }
    
    public void validarCnpj(String cnpj) {
    	if (cnpj == null || cnpj.trim().isEmpty() || cnpj.length() != 18) {
			throw new InvalidDataException("Insira um CNPJ válido", "cnpj");
		}
		else if(empresaRepository.findByCnpj(cnpj).isPresent()) {
			throw new DataAlredyRegistred("CNPJ já está em uso", "cnpj");
		}
    }
    
    public void validarNomeEmpresa(String nome) {
    	if (nome == null || nome.trim().isEmpty()) {
			throw new InvalidDataException("Insira um nome válido", "name");
		}
    }
    
    public void validarDescricaoEmpresa(String descricao) {
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new InvalidDataException("Insira uma descrição válida", "descricao");
		}
    }
    
    public void validarTelefoneEmpresa(String telefone) {
    	if (telefone == null || telefone.trim().isEmpty() || telefone.length() != 14) {
			throw new InvalidDataException("Insira um telefone válido", "telefone");
		}
		else if(telefone.length() < 14) {
			throw new InvalidDataException("O telefone deve ter no mínimo 14 caracteres", "telefone");
		}
    }
    
    public void validarCepEmpresa(String cep) {
    	if(cep == null || cep.trim().isEmpty()) {
			throw new InvalidDataException("Insira um CEP válido", "cep");
		}
		else if(cep.length() < 9) {
			throw new InvalidDataException("O CEP deve ter no mínimo 9 caracteres", "cep");
		}
    }
    
    public void validarEndereçoEmpresa(String rua, String numero, String bairro, String cidade) {

		if (rua == null || rua.trim().isEmpty()) {
			throw new InvalidDataException("Insira uma rua válida", "rua");
		}
		if (bairro == null || bairro.trim().isEmpty()) {
			throw new InvalidDataException("Insira um bairro válido", "bairro");
		}
		if(numero == null || numero.trim().isEmpty()) {
			throw new InvalidDataException("Insira um número válido", "numero");
		}
		if(cidade == null || cidade.trim().isEmpty()) {
			throw new InvalidDataException("Insira uma cidade válida", "cidade");
		}	
    }
    
}
