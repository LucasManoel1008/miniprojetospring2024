package br.com.itb.miniprojetospring.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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
	@Column(columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
	private UUID id = UUID.randomUUID();;

	@Column(name = "cpf",  nullable = false)
	private String cpf;

	private String nome_usuario;


	private Date data_nascimento;
	private String senha_usuario;
	@Column(name = "email_usuario", nullable = false, unique = true, length = 100)
	private String email;


	private LocalDateTime data_criacao_usuario;
	@Column(name = "senha_token", nullable = true, updatable = false)
	private String senhaToken;

	@Column(name = "token")
	private String token;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Senhas_Antigas> senhasAntigas;


	private int nivel_acesso = 1;

	private boolean status_usuario = true;


	// CRIAR GETTERS E SETTERS
	public String getnome_usuario() {
		return nome_usuario;
	}

	public String getSenhaToken() {
		return senhaToken;
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

	public int getNivel_acesso() {
		return nivel_acesso;
	}

	public void setNivel_acesso(int nivel_acesso) {
		this.nivel_acesso = nivel_acesso;
	}

	public boolean isStatus_usuario() {
		return status_usuario;
	}

	public List<Senhas_Antigas> getSenhasAntigas() {
		return senhasAntigas;
	}

	public void setSenhasAntigas(List<Senhas_Antigas> senhasAntigas) {
		this.senhasAntigas = senhasAntigas;
	}

	@Override
	public String toString() {
		return "Usuario {\n" +
				"  cpf='" + cpf + "',\n" +
				"  nome_usuario='" + nome_usuario + "',\n" +
				"  data_nascimento=" + data_nascimento + ",\n" +
				"  senha_usuario='" + senha_usuario + "',\n" +
				"  email='" + email + "',\n" +
				"  data_criacao_usuario=" + data_criacao_usuario + ",\n" +
				"  senhaToken='" + senhaToken + "',\n" +
				"  token='" + token + "',\n" +
				"  senhasAntigas=" + senhasAntigas + "\n" +
				'}';
	}

}
