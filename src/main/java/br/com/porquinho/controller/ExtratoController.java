package br.com.porquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExtratoController {

    @GetMapping("/extrato")
    public String extrato(){
        return "extrato";
    }

}
