package br.com.porquinho.controller;

import br.com.porquinho.model.FormaPagamento;
import br.com.porquinho.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FormaPagamentoController {

    private final FormaPagamentoRepository formaPagamentoRepository;

    FormaPagamentoController(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    @GetMapping("/pegaListaFormaPagamento")
    @ResponseBody
    public List<FormaPagamento> dashboard() {
        return formaPagamentoRepository.listarTodos();
    }


}
