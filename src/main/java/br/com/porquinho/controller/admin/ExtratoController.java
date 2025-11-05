package br.com.porquinho.controller.admin;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.ExtratoService;
import br.com.porquinho.service.TipoGastoService;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ExtratoController {

    private final ExtratoService extratoService;
    private final UsuarioService usuarioService;
    private final TipoGastoService tipoGastoService;

    public ExtratoController(ExtratoService extratoService, UsuarioService usuarioService, TipoGastoService tipoGastoService) {
        this.extratoService = extratoService;
        this.usuarioService = usuarioService;
        this.tipoGastoService = tipoGastoService;
    }

    @GetMapping("/extrato")
    public String extrato(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        model.addAttribute("listaExtrato", extratoService.listarTodos(usuario.getId_usuario()));
        model.addAttribute("listaTipoGasto", tipoGastoService.listarTodos(usuario.getId_usuario()));

        return "pages/admin/extrato/index";
    }

    @PostMapping("/extrato")
    public String registraTransacao(Extrato extratoForm, HttpSession session, HttpServletRequest request) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        extratoService.registraTransacao(extratoForm, usuario);

        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        // retorna a pagina de onde foi enviado o formulario
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
