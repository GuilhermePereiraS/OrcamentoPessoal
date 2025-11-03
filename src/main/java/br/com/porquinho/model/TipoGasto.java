package br.com.porquinho.model;

public class TipoGasto {

    private Integer id_tipo_gasto;
    private String descricao;

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

}
