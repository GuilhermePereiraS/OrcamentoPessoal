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

@Controller
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/auth/cadastro";
    }

    @PostMapping("/persistir")
    public String persistir(@ModelAttribute Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
        usuarioService.salvar(usuario);

        if (usuarioService.encontraPorLogin(usuario.getLogin()) != null) {
            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro efetuado com sucesso!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "success");
            
            return "redirect:/admin/home";
        } 

        redirectAttributes.addFlashAttribute("alerta", true);
        redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro não efetuado!");
        redirectAttributes.addFlashAttribute("iconeAlerta", "error");

        return "redirect:/login";
    }   

}
