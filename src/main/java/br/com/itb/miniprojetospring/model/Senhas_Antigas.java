package br.com.itb.miniprojetospring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Senhas_Antigas {

    @Id
    @Column(columnDefinition = "uniqueidentifier", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // ou cpf, se estiver usando como chave
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
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
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
