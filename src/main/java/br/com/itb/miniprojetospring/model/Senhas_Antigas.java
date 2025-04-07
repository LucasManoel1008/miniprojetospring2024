package br.com.itb.miniprojetospring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Senhas_Antigas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "usuario_cpf") // ou cpf, se estiver usando como chave
    @JsonBackReference
    private Usuario usuario;

    // Construtor padrão
    public Senhas_Antigas() {
    }
    public Senhas_Antigas(String senha, Usuario usuario) {
        this.senha = senha;
        this.usuario = usuario;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
