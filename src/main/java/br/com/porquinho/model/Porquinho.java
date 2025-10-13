package br.com.porquinho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Porquinho implements Serializable {

    @Id
    private Integer id_porquinho;

    private Integer id_usuario;
    private String nome_meta;
    private BigDecimal dinheiro_alcancado;
    private BigDecimal dinheiro_necessario;
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

    public BigDecimal getDinheiro_alcancado() {
        return dinheiro_alcancado;
    }

    public void setDinheiro_alcancado(BigDecimal dinheiro_alcancado) {
        this.dinheiro_alcancado = dinheiro_alcancado;
    }

    public BigDecimal getDinheiro_necessario() {
        return dinheiro_necessario;
    }

    public void setDinheiro_necessario(BigDecimal dinheiro_necessario) {
        this.dinheiro_necessario = dinheiro_necessario;
    }

    public LocalDate getDt_meta() {
        return dt_meta;
    }

    public void setDt_meta(LocalDate dt_meta) {
        this.dt_meta = dt_meta;
    }
}
