package br.com.porquinho.dto;

import br.com.porquinho.model.Extrato;

public class ExtratoDTO {
    private String listaItensJson;
    private Extrato extratoForm;

    public String getListaItensJson() {
        return listaItensJson;
    }

    public void setListaItensJson(String listaItensJson) {
        this.listaItensJson = listaItensJson;
    }

    public Extrato getExtratoForm() {
        return extratoForm;
    }

    public void setExtratoForm(Extrato extratoForm) {
        this.extratoForm = extratoForm;
    }
}
