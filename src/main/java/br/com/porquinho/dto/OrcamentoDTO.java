package br.com.porquinho.dto;

import java.math.BigDecimal;

public class OrcamentoDTO {
    private Integer id_orcamento;
    private Integer id_tipo_gasto;
    private BigDecimal limite;

    public Integer getId_orcamento() {
        return id_orcamento;
    }

    public void setId_orcamento(Integer id_orcamento) {
        this.id_orcamento = id_orcamento;
    }

    public Integer getId_tipo_gasto() {
        return id_tipo_gasto;
    }

    public void setId_tipo_gasto(Integer id_tipo_gasto) {
        this.id_tipo_gasto = id_tipo_gasto;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }
}
