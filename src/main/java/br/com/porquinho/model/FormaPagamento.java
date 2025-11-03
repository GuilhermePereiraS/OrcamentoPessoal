package br.com.porquinho.model;

public class FormaPagamento {

    private Integer id_forma_pgmt;
    private String descricao;

    public Integer getId_forma_pgmt() {
        return id_forma_pgmt;
    }

    public void setId_forma_pgmt(Integer id_forma_pgmt) {
        this.id_forma_pgmt = id_forma_pgmt;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
