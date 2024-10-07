package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Usuario")
public class Usuario {

	Usuario(){

	}

	public Usuario(long id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;	

	private String nome;

	private String ultimonome_usuario;
	// Chave Estrangeira - Empresa
	@OneToOne
	@JoinColumn(name = "id_empresa")
	private Empresa id_empresa;

	private Date data_nascimento;
	private String senha_usuario;
	private String email_usuario;		 
	private String CPF;
	
	private Date data_criacao_usuario;
	
	// CRIAR GETTERS E SETTERS


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUltimonome_usuario() {
		return ultimonome_usuario;
	}

	public void setUltimonome_usuario(String ultimonome_usuario) {
		this.ultimonome_usuario = ultimonome_usuario;
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

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public Date getData_criacao_usuario() {
		return data_criacao_usuario;
	}

	public void setData_criacao_usuario(Date data_criacao_usuario) {
		this.data_criacao_usuario = data_criacao_usuario;
	}
}
