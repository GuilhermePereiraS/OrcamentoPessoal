package br.com.porquinho.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrcamentoController {

    @GetMapping("/orcamento")
    public String index() {
        return "pages/admin/orcamento/orcamento";
    }

}
