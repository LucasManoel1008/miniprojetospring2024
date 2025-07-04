package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Empresa")
public class Empresa {

    @Id
    @Column(columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    private String cnpj;

    private String nome_empresa;
    private String telefone_empresa;
    @Column(name = "logo_empresa")
    private byte[] foto;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String descricao_empresa;
    private LocalDateTime data_criacao;

    // Construtores
    public Empresa() {
        this.data_criacao = LocalDateTime.now(); // Define a data de criação automaticamente
    }

    public Empresa(String cnpj, String nome_empresa) {
        this();
        this.cnpj = cnpj;
        this.nome_empresa = nome_empresa;
    }
//    Getter's e Setter's


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getTelefone_empresa() {
        return telefone_empresa;
    }

    public void setTelefone_empresa(String telefone_empresa) {
        this.telefone_empresa = telefone_empresa;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }



    public String getDescricao_empresa() {
        return descricao_empresa;
    }

    public void setDescricao_empresa(String descricao_empresa) {
        this.descricao_empresa = descricao_empresa;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }
    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    
    @Override
    public String toString() {
		return "Empresa{" +
				"cnpj='" + cnpj + '\'' +
				", nome_empresa='" + nome_empresa + '\'' +
				", telefone_empresa='" + telefone_empresa + '\'' +
				", foto=" + (foto != null ? foto.length : 0) + " bytes" +
				", rua='" + rua + '\'' +
				", numero='" + numero + '\'' +
				", bairro='" + bairro + '\'' +
				", cidade='" + cidade + '\'' +
				", cep='" + cep + '\'' +
				", usuario=" + usuario +
				", descricao_empresa='" + descricao_empresa + '\'' +
				", data_criacao=" + data_criacao +
				'}';
	}
}



