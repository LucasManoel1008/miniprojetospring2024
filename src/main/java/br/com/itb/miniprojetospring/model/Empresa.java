package br.com.itb.miniprojetospring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    
    private String email_empresa;
    
    private String cidade_empresa;
    
    private String descricao_empresa;

    private Date data_criacao;



//    Getter's e Setter's

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

    public String getEmail_empresa() {
        return email_empresa;
    }

    public void setEmail_empresa(String email_empresa) {
        this.email_empresa = email_empresa;
    }

    public String getCidade_empresa() {
        return cidade_empresa;
    }

    public void setCidade_empresa(String cidade_empresa) {
        this.cidade_empresa = cidade_empresa;
    }

    public String getDescricao_empresa() {
        return descricao_empresa;
    }

    public void setDescricao_empresa(String descricao_empresa) {
        this.descricao_empresa = descricao_empresa;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }
}



