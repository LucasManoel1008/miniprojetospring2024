package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Servico")
public class Servico {
    Servico(){

    }
    public Servico(long id, String nome_servico, String descricao_servico){
        this.nome_servico = nome_servico;
        this.descricao_servico = descricao_servico;
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome_servico;
    private String descricao_servico;
    // Chave Estrangeira - Empresa
    @ManyToOne
    @JoinColumn(name = "cnpj_empresa", referencedColumnName = "cnpj")
    private Empresa empresa;


//    Getter's e Setter's


    public long getId() {
        return id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setEmpresa(Empresa empresa) {
    }
}
