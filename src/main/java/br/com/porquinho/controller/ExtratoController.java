package br.com.porquinho.controller;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.ExtratoService;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExtratoController {

    private final ExtratoService extratoService;
    private final UsuarioService usuarioService;

    public ExtratoController(ExtratoService extratoService, UsuarioService usuarioService) {
        this.extratoService = extratoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/extrato")
    public String extrato(){
        return "extrato";
    }

    @PostMapping("/registraTransacao")
    public String registraTransacao(Extrato extratoForm, HttpSession session, HttpServletRequest request) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        extratoService.registraTransacao(extratoForm, usuario);

        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        // retorna a pagina de onde foi enviado o formulario
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
