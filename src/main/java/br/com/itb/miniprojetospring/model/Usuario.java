package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name="Usuario")
public class Usuario {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


	public Usuario(String cpf, String nome_usuario){
		this.cpf = cpf;
		this.nome_usuario = nome_usuario;
	}
	public Usuario() {
		this.data_criacao_usuario = Instant.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
	}
	
	@Id
	@Column(name = "cpf",  nullable = false)
	private String cpf;

	private String nome_usuario;


	// Chave Estrangeira - Empresa


	private Date data_nascimento;
	private String senha_usuario;
	@Column(name = "email_usuario", nullable = false, unique = true, length = 100)
	private String email;


	private LocalDateTime data_criacao_usuario;
	@Column(name = "senha_token", nullable = true, updatable = false)
	private String senhaToken;
	@Column(name = "expiracao_token", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private LocalDateTime expiracao_token;
	@Column(name = "token")
	private String token;
	
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

	public void setData_nascimento(Date data_nascimento) throws ParseException {
		this.data_nascimento = sdf.parse(sdf.format(data_nascimento));
	}

	public String getSenha_usuario() {
		return senha_usuario;
	}

	public void setSenha_usuario(String senha_usuario) {
		this.senha_usuario = senha_usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDateTime getData_criacao_usuario() {
		return data_criacao_usuario;
	}
	public void setSenhaToken(String senhaToken) {
		this.senhaToken = senhaToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setData_criacao_usuario(LocalDateTime data_criacao_usuario) {
		this.data_criacao_usuario = data_criacao_usuario;
	}


	public boolean isTokenValido() {
		return expiracao_token != null && LocalDateTime.now().isBefore(expiracao_token);
	}
}
