package br.com.porquinho.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

import static br.com.porquinho.util.PorquinhoUtils.criaMensagemErro;
import static br.com.porquinho.util.PorquinhoUtils.criaMensagemSucesso;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/auth/login";
    }

    @PostMapping("/login")
    public String logar(@ModelAttribute  Usuario usuarioForm, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            Usuario usuarioEncotrado = usuarioService.encontraPorLoginEHashSenha(usuarioForm.getLogin(), usuarioForm.getSenha());
            session.setAttribute("usuarioLogado", usuarioEncotrado);
            session.setAttribute("mesAtual", LocalDate.now().getMonthValue());
            session.setAttribute("anoAtual", LocalDate.now().getYear());
            criaMensagemSucesso(redirectAttributes, "Usuário logado com sucesso!");
            return "redirect:/admin/home";
        } catch (Exception e) {
            criaMensagemErro(redirectAttributes, e.getMessage());
            return "redirect:/login";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("usuarioLogado");
        return "redirect:/admin/home";
    }
}
