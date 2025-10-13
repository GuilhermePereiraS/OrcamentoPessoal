package br.com.porquinho.controller;


import br.com.porquinho.Repository.UsuarioRepository;
import br.com.porquinho.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/persistir")
    public String persistir(@ModelAttribute  Usuario usuario, Model model) {
        usuarioRepository.save(usuario);
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
         Usuario usuario = usuarioRepository.findByLoginESenha(usuarioForm.getLogin(), usuarioForm.getSenha());

         if (usuario == null) {
             model.addAttribute("alertaRuim", true);
             model.addAttribute("mensagem", "Usuario não encontrado!");
             return "index";
         } else {
             model.addAttribute("alerta", true);
             model.addAttribute("mensagem", "Usuario Logado com sucesso!");

             session.setAttribute("usuarioLogado", usuario);
            return "redirect:dashboard";
         }

    }
}
