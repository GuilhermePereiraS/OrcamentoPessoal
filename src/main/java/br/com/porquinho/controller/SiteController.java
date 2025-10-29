package br.com.porquinho.controller;

import br.com.porquinho.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/index";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/auth/cadastro";
    }
}
