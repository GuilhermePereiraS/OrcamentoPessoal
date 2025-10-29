package br.com.porquinho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Extrato {

    @Id
    private Integer id_extrato;
    Integer Id_usuario ;
    Integer id_forma_pgmt;
    Integer id_tipo_gasto ;
    String  descricao ;
    BigDecimal vl_transacao;
    LocalDate dt_transacao;
    String tp_transacao;

    public enum tipoTransacao {
        ENTRADA("entrada"),
        SAIDA("saida");

        private String operacao;

        tipoTransacao(String operacao) {
            this.operacao = operacao;
        }

        public String getOperacao() {
            return operacao;
        }
    }


    public Integer getId_extrato() {
        return id_extrato;
    }

    public void setId_extrato(Integer id_extrato) {
        this.id_extrato = id_extrato;
    }

    public Integer getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        Id_usuario = id_usuario;
    }

    public Integer getId_forma_pgmt() {
        return id_forma_pgmt;
    }

    public void setId_forma_pgmt(Integer id_forma_pgmt) {
        this.id_forma_pgmt = id_forma_pgmt;
    }

    public Integer getId_tipo_gasto() {
        return id_tipo_gasto;
    }

    public void setId_tipo_gasto(Integer id_tipo_gasto) {
        this.id_tipo_gasto = id_tipo_gasto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTp_transacao() {
        return tp_transacao;
    }

    public void setTp_transacao(String tp_transacao) {
        this.tp_transacao = tp_transacao;
    }

    public BigDecimal getVl_transacao() {
        return vl_transacao;
    }

    public void setVl_transacao(BigDecimal vl_transacao) {
        this.vl_transacao = vl_transacao;
    }

    public LocalDate getDt_transacao() {
        return dt_transacao;
    }

    public void setDt_transacao(LocalDate dt_transacao) {
        this.dt_transacao = dt_transacao;
    }
}


