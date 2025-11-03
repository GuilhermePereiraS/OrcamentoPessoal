package br.com.porquinho.controller;

import br.com.porquinho.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/")
    public String index(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/login";
        }
    }

}