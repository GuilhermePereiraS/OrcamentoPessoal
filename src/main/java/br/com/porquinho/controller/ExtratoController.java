package br.com.porquinho.controller;

import br.com.porquinho.dto.TransacaoDTO;
import br.com.porquinho.model.Extrato;
import br.com.porquinho.service.ExtratoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.porquinho.model.Extrato.tipoTransacao;

@Controller
public class ExtratoController {

    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

    @GetMapping("/extrato")
    public String extrato(){
        return "extrato";
    }

    @PostMapping("/registraTransacao")
    public String entrada(TransacaoDTO transacaoDTO) {
        extratoService.registraTransacao(transacaoDTO);

        return "home";
    }

}
