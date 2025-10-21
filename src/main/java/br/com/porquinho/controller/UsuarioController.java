package br.com.porquinho.controller;


import br.com.porquinho.repository.UsuarioRepository;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String persistir(@ModelAttribute  Usuario usuario, Model model) {
        usuarioService.salvar(usuario);
        model.addAttribute("alerta", true);
        model.addAttribute("mensagem", "Cadastro efetuado com sucesso!");
        return "index";
    }

    @GetMapping("/")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "index";
    }

    @PostMapping("/login")
    public String logar(@ModelAttribute  Usuario usuarioForm, Model model, HttpSession session) {
         Usuario usuario = usuarioService.encontraPorLoginEHashSenha(usuarioForm.getLogin(), usuarioForm.getSenha());

         if (usuario == null) {
             model.addAttribute("alertaRuim", true);
             model.addAttribute("mensagem", "Usuário não encontrado!");
             return "index";
         } else {
             model.addAttribute("alerta", true);
             model.addAttribute("mensagem", "Usuário logado com sucesso!");

             session.setAttribute("usuarioLogado", usuario);
             session.setAttribute("nomeUsuario", usuario.getLogin());
            return "redirect:dashboard";
         }

    }
}
