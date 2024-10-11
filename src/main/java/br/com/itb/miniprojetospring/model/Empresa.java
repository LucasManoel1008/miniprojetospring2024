package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "Empresa")
public class Empresa {
    Empresa(){

    }
    public Empresa(long id, String nome_empresa){
        this.nome_empresa = nome_empresa;
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome_empresa;

    private String telefone_empresa;

    private byte[] foto;
    private String cnpj;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String cep;
    @OneToOne
    @JoinColumn(name = "cpf_usuario")
    private Usuario usuario;
    
    private String descricao_empresa;

    private LocalDateTime data_criacao;



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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}



