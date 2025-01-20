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
    private String valor_estimado_servico;
    private String criterios_servico;
    private String tempo_servico;
    // Chave Estrangeira - Empresa
    @ManyToOne
    @JoinColumn(name = "cnpj_empresa", referencedColumnName = "cnpj")
    private Empresa empresa;
    
    private Boolean status_servico;

//    Getter's e Setter's

    public String getCriterios_servico() {
        return criterios_servico;
    }
    public void setCriterios_servico(String criterios_servico) {
        this.criterios_servico = criterios_servico;
    }
    public Boolean getStatus_servico() {
        return status_servico;
    }
    public void setStatus_servico(Boolean status_servico) {
        this.status_servico = status_servico;
    }
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

    public String getTempo_servico() {
        return tempo_servico;
    }

    public void setTempo_servico(String tempo_servico) {
        this.tempo_servico = tempo_servico;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", disponibilidade_servico=" + disponibilidade_servico +
                ", tempo_servico='" + tempo_servico + '\'' +
                '}';
    }
}
