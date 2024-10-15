package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="Usuario")
public class Usuario {



	public Usuario(Long cpf, String nome_usuario){
		this.cpf =cpf;
		this.nome_usuario = nome_usuario;
	}
	public Usuario() {
		this.data_criacao_usuario = LocalDateTime.now(); // Define a data de criação automaticamente
	}
	
	@Id
	@Column(name = "cpf",  nullable = false)
	private Long cpf;

	private String nome_usuario;


	// Chave Estrangeira - Empresa


	private Date data_nascimento;
	private String senha_usuario;
	private String email_usuario;		 


	private LocalDateTime data_criacao_usuario;
	
	// CRIAR GETTERS E SETTERS


	public String getnome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}



	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSenha_usuario() {
		return senha_usuario;
	}

	public void setSenha_usuario(String senha_usuario) {
		this.senha_usuario = senha_usuario;
	}

	public String getEmail_usuario() {
		return email_usuario;
	}

	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public LocalDateTime getData_criacao_usuario() {
		return data_criacao_usuario;
	}

	public void setData_criacao_usuario(LocalDateTime data_criacao_usuario) {
		this.data_criacao_usuario = data_criacao_usuario;
	}
}
