package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "Orcamento")
public class Orcamento {
    Orcamento(){

    }
    public Orcamento(long id){
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "id_servico")
    private Servico id_servico;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;
    @OneToOne
    @JoinColumn(name = "id_empresa")
    private Empresa id_empresa;


    private double valor_servico;

    private LocalDateTime data_assinatura;





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

    public LocalDateTime getData_assinatura() {
        return data_assinatura;
    }

    public void setData_assinatura(LocalDateTime data_assinatura) {
        this.data_assinatura = data_assinatura;
    }
}