package br.com.porquinho.model;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class Orcamento {
    private Integer idUsuario;
    private Integer id_orcamento;
    private int mes;
    private int ano;
    private BigDecimal limite_mensal;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getId_orcamento() {
        return id_orcamento;
    }

    public void setId_orcamento(Integer id_orcamento) {
        this.id_orcamento = id_orcamento;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public BigDecimal getLimite_mensal() {
        return limite_mensal;
    }

    public void setLimite_mensal(BigDecimal limite_mensal) {
        this.limite_mensal = limite_mensal;
    }
}
