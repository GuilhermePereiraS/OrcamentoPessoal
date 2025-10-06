package br.com.porquinho.controller;

import br.com.porquinho.Repository.UsuarioRepository;
import br.com.porquinho.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
    @ResponseBody
    public String persistir(@ModelAttribute  Usuario usuario) {
        usuarioRepository.save(usuario);
        return "<h1>Cadastrado!</h1>";
    }
}
