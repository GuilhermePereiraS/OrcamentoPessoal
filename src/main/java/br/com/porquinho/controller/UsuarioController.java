package br.com.porquinho.controller;


import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/persistir")
    public String persistir(@ModelAttribute Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
        usuarioService.salvar(usuario);

        if (usuarioService.encontraPorLogin(usuario.getLogin()) != null) {
            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro efetuado com sucesso!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "success");
        } else {
            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro não efetuado!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "error");
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "pages/login";
    }

    @PostMapping("/login")
    public String logar(@ModelAttribute  Usuario usuarioForm, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
         Usuario usuarioEncotrado = usuarioService.encontraPorLoginEHashSenha(usuarioForm.getLogin(), usuarioForm.getSenha());

        redirectAttributes.addFlashAttribute("alerta", true);
        if (usuarioEncotrado == null) {
            redirectAttributes.addFlashAttribute("tituloAlerta", "Deu ruim!");
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Usuario ou senha incorretos!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "warning");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("tituloAlerta", "Sucesso!");
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Usuário logado com sucesso!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "success");
        }

        session.setAttribute("usuarioLogado", usuarioEncotrado);
        session.setAttribute("nomeUsuario", usuarioEncotrado.getLogin());
        return "redirect:/dashboard";
    }

}
