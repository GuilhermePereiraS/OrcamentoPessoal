package br.com.porquinho.controller;

import br.com.porquinho.Repository.PorquinhoRepository;
import br.com.porquinho.model.Porquinho;
import br.com.porquinho.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class PorquinhoController {

    @Autowired
    private PorquinhoRepository porquinhoRepository;

    @PostMapping("/persistirPorquinho")
    public String persistirPorquinho(@ModelAttribute Porquinho porquinho, HttpSession session) {
        Usuario usuariolLogado = (Usuario) session.getAttribute("usuarioLogado");
        porquinho.setId_usuario(usuariolLogado.getId_usuario());

        porquinhoRepository.save(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/editarPorquinho")
    public String editarPorquinho(@ModelAttribute Porquinho porquinho, HttpSession session) {
        porquinhoRepository.update(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/excluirPorquinho")
    public String excluirPorquinho(@ModelAttribute Porquinho porquinho, HttpSession session) {
        porquinhoRepository.delete(porquinho);
        return "redirect:/porquinho";
    }

    @PostMapping("/acaoPorquinho")
    public String acaoPorquinho(@RequestParam String acao) {
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

        List<Porquinho> listaPorquinhos = porquinhoRepository.findAll(usuarioLogado.getId_usuario());
        model.addAttribute("listaPorquinhos", listaPorquinhos);
        return "porquinho";
    }
}
