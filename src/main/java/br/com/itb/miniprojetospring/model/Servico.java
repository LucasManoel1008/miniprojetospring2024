package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.util.Date;

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
    private String categoria_servico;
    private String local_servico;
    private Date disponibilidade_servico;
    private String materiais_servico;
    private byte[] imagem_servico;
    private String valor_estimado_servico;
    // Chave Estrangeira - Empresa
    @ManyToOne
    @JoinColumn(name = "cnpj_empresa", referencedColumnName = "cnpj")
    private Empresa empresa;


//    Getter's e Setter's


    public String getCategoria_servico() {
        return categoria_servico;
    }

    public void setCategoria_servico(String categoria_servico) {
        this.categoria_servico = categoria_servico;
    }

    public String getLocal_servico() {
        return local_servico;
    }

    public void setLocal_servico(String local_servico) {
        this.local_servico = local_servico;
    }

    public Date getDisponibilidade_servico() {
        return disponibilidade_servico;
    }

    public void setDisponibilidade_servico(Date disponibilidade_servico) {
        this.disponibilidade_servico = disponibilidade_servico;
    }

    public String getMateriais_servico() {
        return materiais_servico;
    }

    public void setMateriais_servico(String materiais_servico) {
        this.materiais_servico = materiais_servico;
    }

    public byte[] getImagem_servico() {
        return imagem_servico;
    }

    public void setImagem_servico(byte[] imagem_servico) {
        this.imagem_servico = imagem_servico;
    }

    public String getValor_estimado_servico() {
        return valor_estimado_servico;
    }

    public void setValor_estimado_servico(String valor_estimado_servico) {
        this.valor_estimado_servico = valor_estimado_servico;
    }

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
        this.empresa = empresa;
    }
}
