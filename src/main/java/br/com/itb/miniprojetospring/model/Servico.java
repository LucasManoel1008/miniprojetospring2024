package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Servicos")
public class Servico {
    Servico(){

    }
    public Servico(long id, String nome_servico){
        this.nome_servico = nome_servico;
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome_servico;
    private String descricao_servico;


//    Getter's e Setter's


    public long getId_servicos() {
        return id;
    }

    public void setId_servicos(long id_servicos) {
        this.id = id_servicos;
    }

    public String getNome_servico() {
        return nome_servico;
    }

    public void setNome_servico(String nome_servico) {
        this.nome_servico = nome_servico;
    }

    public String getDescricao_servico() {
        return descricao_servico;
    }

    public void setDescricao_servico(String descricao_servico) {
        this.descricao_servico = descricao_servico;
    }
}
