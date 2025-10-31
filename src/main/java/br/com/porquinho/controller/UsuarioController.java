package br.com.porquinho.controller;


import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.porquinho.service.LoginJaExistenteException;

@Controller
class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "cadastro";
    }

    @PostMapping("/persistir")
    public String persistir(@Valid @ModelAttribute("usuario")  Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {

            String primeiraMensagem = bindingResult.getAllErrors().getFirst().getDefaultMessage();

            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", primeiraMensagem);
            redirectAttributes.addFlashAttribute("iconeAlerta", "error");

            redirectAttributes.addFlashAttribute("usuario", usuario);

            return "redirect:/cadastro";
        }

        try {
            usuarioService.salvar(usuario);

            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Cadastro efetuado com sucesso!");
            redirectAttributes.addFlashAttribute("iconeAlerta", "success");
            return "redirect:/";

        } catch (LoginJaExistenteException e) {

            redirectAttributes.addFlashAttribute("alerta", true);
            redirectAttributes.addFlashAttribute("mensagemAlerta", e.getMessage());
            redirectAttributes.addFlashAttribute("iconeAlerta", "error");
            redirectAttributes.addFlashAttribute("usuario", usuario);
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/")
    public String paginaLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "index";
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

        // adiciona o transacaoDTO na sessão, descobrir uma forma mais elegante de manipulação
        session.setAttribute("usuario", usuarioEncotrado.getLogin());

        return "redirect:/dashboard";
    }

}
