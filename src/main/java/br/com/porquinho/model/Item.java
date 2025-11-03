package br.com.porquinho.model;

import java.math.BigDecimal;

public class Item {
    private Integer id_item;
    private String nome;
    private BigDecimal vl_unitario;
    private BigDecimal vl_total;
    private Integer quantidade;

    public Integer getId_item() {
        return id_item;
    }

    public void setId_item(Integer id_item) {
        this.id_item = id_item;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getVl_unitario() {
        return vl_unitario;
    }

    public void setVl_unitario(BigDecimal vl_unitario) {
        this.vl_unitario = vl_unitario;
    }

    public BigDecimal getVl_total() {
        return vl_total;
    }

    public void setVl_total(BigDecimal vl_total) {
        this.vl_total = vl_total;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
