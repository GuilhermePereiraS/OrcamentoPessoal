package br.com.porquinho.controller.admin;

import br.com.porquinho.model.Porquinho;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.PorquinhoService;
import br.com.porquinho.service.UsuarioService;
import br.com.porquinho.util.Aviso;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.com.porquinho.util.PorquinhoUtils.criaMensagemAlerta;
import static br.com.porquinho.util.PorquinhoUtils.criaMensagemSucesso;

@Controller
public class PorquinhoController {

    private final PorquinhoService porquinhoService;
    private final UsuarioService usuarioService;

    public PorquinhoController(PorquinhoService porquinhoService, UsuarioService usuarioService) {
        this.porquinhoService = porquinhoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/persistirPorquinho")
    public String persistirPorquinho(Porquinho porquinho, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        porquinho.setId_usuario(usuarioLogado.getId_usuario());

        try {
            try {
                porquinhoService.salvar(porquinho);
                criaMensagemSucesso(redirectAttributes, "Porquinho salvo com sucesso!");
            } catch (Aviso e) {
                criaMensagemAlerta(redirectAttributes, e.getMessage());
            }

        } catch (Exception e) {
            criaMensagemAlerta(redirectAttributes, e.getMessage());
        }
        return "redirect:/admin/porquinho";
    }

    @PostMapping("/editarPorquinho")
    public String editarPorquinho(Porquinho porquinho, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        porquinho.setId_usuario(usuarioLogado.getId_usuario());
        try {
            try {
                porquinhoService.atualizar(porquinho);
                criaMensagemSucesso(redirectAttributes, "Porquinho editado com sucesso!");
            } catch (Aviso e) {
                criaMensagemAlerta(redirectAttributes, e.getMessage());
            }

        } catch (Exception e) {
            criaMensagemAlerta(redirectAttributes, e.getMessage());
        }
        return "redirect:/admin/porquinho";
    }

    @PostMapping("/excluirPorquinho")
    public String excluirPorquinho(Porquinho porquinho, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        porquinho.setId_usuario(usuarioLogado.getId_usuario());

        try {
            try {
                porquinhoService.excluir(porquinho);
                criaMensagemSucesso(redirectAttributes, "Porquinho excluido com sucesso!");
            } catch (Aviso e) {
                criaMensagemAlerta(redirectAttributes, e.getMessage());
            }

        } catch (Exception e) {
            criaMensagemAlerta(redirectAttributes, e.getMessage());
        }
        return "redirect:/admin/porquinho";
    }

    @PostMapping("/acaoPorquinho")
    public String acaoPorquinho(@RequestParam String acao, Porquinho porquinho) {
        if (acao.equals("excluir")) {
            return "forward:/excluirPorquinho";
        } else if (acao.equals("editar")) {
            return "forward:/editarPorquinho";
        }
        return "redirect:admin/porquinho";
    }

    @GetMapping("/admin/porquinho")
    public String porquinho(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Usuario usuarioNoBanco = usuarioService.encontraPorLogin(usuarioLogado.getLogin());
        session.setAttribute("usuarioLogado", usuarioNoBanco);

        model.addAttribute("porquinhoModel", new Porquinho());

        List<Porquinho> listaPorquinhos = porquinhoService.listarTodos(usuarioLogado.getId_usuario());
        model.addAttribute("listaPorquinhos", listaPorquinhos);
        return "pages/admin/porquinho/porquinho";
    }
}
