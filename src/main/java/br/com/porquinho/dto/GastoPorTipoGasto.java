package br.com.porquinho.dto;

import java.math.BigDecimal;

public class GastoPorTipoGasto {
    private Integer idTipoGasto;
    private BigDecimal valorGasto;

    public GastoPorTipoGasto(Integer idTipoGasto, BigDecimal valorGasto) {
        this.idTipoGasto = idTipoGasto;
        this.valorGasto = valorGasto;
    }

    public BigDecimal getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(BigDecimal valorGasto) {
        this.valorGasto = valorGasto;
    }

    public Integer getIdTipoGasto() {
        return idTipoGasto;
    }

    public void setIdTipoGasto(Integer idTipoGasto) {
        this.idTipoGasto = idTipoGasto;
    }
}
