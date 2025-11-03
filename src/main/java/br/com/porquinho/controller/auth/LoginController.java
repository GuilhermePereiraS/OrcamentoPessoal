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
        Usuario usuarioEncotrado = usuarioService.encontraPorLoginEHashSenha(usuarioForm.getLogin(), usuarioForm.getSenha());

        redirectAttributes.addFlashAttribute("alerta", true);
        if (usuarioEncotrado == null) {
            redirectAttributes.addFlashAttribute("tituloAlerta", "Deu ruim!");
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Usuario ou senha incorretos!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "warning");

            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("tituloAlerta", "Sucesso!");
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Usuário logado com sucesso!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "success");
        }

        session.setAttribute("usuarioLogado", usuarioEncotrado);
        return "redirect:/admin/home";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("usuarioLogado");
        return "redirect:/admin/home";
    }
}
