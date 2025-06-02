package br.com.itb.miniprojetospring.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Adm")
public class Adm {

    public Adm() {
    }

    public Adm(String id, String nome_adm, String senha_adm) {
        this.id = id;
        this.nome = nome_adm;
        this.senha_adm = senha_adm;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "nome_adm")
    private String nome;

    @Column(name = "senha_adm")
    private String senha_adm;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha_adm() {
        return senha_adm;
    }

    public void setSenha_adm(String senha_adm) {
        this.senha_adm = senha_adm;
    }
}
