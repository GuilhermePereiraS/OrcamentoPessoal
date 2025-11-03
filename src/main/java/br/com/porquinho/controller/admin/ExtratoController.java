package br.com.porquinho.controller.admin;

import br.com.porquinho.dto.TransacaoDTO;
import br.com.porquinho.service.ExtratoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
