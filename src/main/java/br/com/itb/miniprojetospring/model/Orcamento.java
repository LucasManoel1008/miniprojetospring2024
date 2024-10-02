package br.com.itb.miniprojetospring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;


@Entity
@Table(name = "Orcamento")
public class Orcamento {
    Orcamento(){

    }
    public Orcamento(long id, String nome_orcamento){
        this.nome_orcamento = nome_orcamento;
        this.id = id;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private double valor_servico;

    private Date data_assinatura;





//    Getter's e Setter's

  public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValor_servico() {
        return valor_servico;
    }

    public void setValor_servico(double valor_servico) {
        this.valor_servico = valor_servico;
    }

    public Date getData_assinatura() {
        return data_assinatura;
    }

    public void setData_assinatura(Date data_assinatura) {
        this.data_assinatura = data_assinatura;
    }
}