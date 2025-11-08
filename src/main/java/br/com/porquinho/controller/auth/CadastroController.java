package br.com.porquinho.controller.auth;

import br.com.porquinho.service.LoginJaExistenteException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String persistir(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String primeiraMensagem = bindingResult.getAllErrors().getFirst().getDefaultMessage();

            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", primeiraMensagem);

            redirectAttributes.addFlashAttribute("usuario", usuario);

            return "redirect:/cadastro";
        }

        try {
            usuarioService.salvar(usuario);
            if (usuarioService.encontraPorLogin(usuario.getLogin()) != null) {
                session.setAttribute("usuarioLogado", usuario);

                redirectAttributes.addFlashAttribute("alerta", true);
                redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro efetuado com sucesso!");
                redirectAttributes.addFlashAttribute("iconeAlerta", "success");
                return "redirect:/admin/home";
            } else {
                redirectAttributes.addFlashAttribute("alerta", true);
                redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro não efetuado por motivo insesperado!");
            }

            return "redirect:/cadastro";
        } catch (LoginJaExistenteException e) {
            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", e.getMessage());
            redirectAttributes.addFlashAttribute("iconeAlerta", "error");

            redirectAttributes.addFlashAttribute("usuario", usuario);

            return "redirect:/cadastro";
        }
    }

}


