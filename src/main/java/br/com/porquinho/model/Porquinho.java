package br.com.porquinho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


public class Porquinho implements Serializable {

    private Integer id_porquinho;

    private Integer id_usuario;
    private String nome_meta;
    private BigDecimal vl_alcancado;
    private BigDecimal vl_necessario;
    private LocalDate dt_meta;

    public Integer getId_porquinho() {
        return id_porquinho;
    }

    public void setId_porquinho(Integer id_porquinho) {
        this.id_porquinho = id_porquinho;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_meta() {
        return nome_meta;
    }

    public void setNome_meta(String nome_meta) {
        this.nome_meta = nome_meta;
    }

    public LocalDate getDt_meta() {
        return dt_meta;
    }

    public void setDt_meta(LocalDate dt_meta) {
        this.dt_meta = dt_meta;
    }

    public BigDecimal getVl_alcancado() {
        return vl_alcancado;
    }

    public void setVl_alcancado(BigDecimal vl_alcancado) {
        this.vl_alcancado = vl_alcancado;
    }

    public BigDecimal getVl_necessario() {
        return vl_necessario;
    }

    public void setVl_necessario(BigDecimal vl_necessario) {
        this.vl_necessario = vl_necessario;
    }
}
