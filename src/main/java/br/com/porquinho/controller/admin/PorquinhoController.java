package br.com.porquinho.controller.admin;

import br.com.porquinho.model.Porquinho;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.PorquinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PorquinhoController {

    private final PorquinhoService porquinhoService;

    public PorquinhoController(PorquinhoService porquinhoService) {
        this.porquinhoService = porquinhoService;
    }

    @PostMapping("/persistirPorquinho")
    public String persistirPorquinho(Porquinho porquinho, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        porquinho.setId_usuario(usuarioLogado.getId_usuario());

        porquinhoService.salvar(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/editarPorquinho")
    public String editarPorquinho(Porquinho porquinho, HttpSession session) {
        porquinhoService.atualizar(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/excluirPorquinho")
    public String excluirPorquinho(Porquinho porquinho, HttpSession session) {
        porquinhoService.excluir(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/acaoPorquinho")
    public String acaoPorquinho(@RequestParam String acao, Porquinho porquinho) {
        if (acao.equals("excluir")) {
            return "forward:/excluirPorquinho";
        } else if (acao.equals("editar")) {
            return "forward:/editarPorquinho";
        }
        return "redirect:/porquinho";
    }

    @GetMapping("/porquinho")
    public String porquinho(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        model.addAttribute("porquinhoModel", new Porquinho());

        List<Porquinho> listaPorquinhos = porquinhoService.listarTodos(usuarioLogado.getId_usuario());
        model.addAttribute("listaPorquinhos", listaPorquinhos);
        return "porquinho";
    }
}
