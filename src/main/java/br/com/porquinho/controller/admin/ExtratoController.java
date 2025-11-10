package br.com.porquinho.controller.admin;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Item;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ExtratoController {

    private final ExtratoService extratoService;
    private final UsuarioService usuarioService;
    private final TipoGastoService tipoGastoService;
    private final ItemService itemService;
    private final FormaPagamentoService formaPagamentoService;

    public ExtratoController(ExtratoService extratoService, UsuarioService usuarioService, TipoGastoService tipoGastoService, ItemService itemService, FormaPagamentoService formaPagamentoService) {
        this.extratoService = extratoService;
        this.usuarioService = usuarioService;
        this.tipoGastoService = tipoGastoService;
        this.itemService = itemService;
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping("/extrato")
    public String extrato(Model model, HttpSession session) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        model.addAttribute("listaExtrato", extratoService.listarTodos(usuario.getId_usuario()));
        model.addAttribute("listaTipoGasto", tipoGastoService.listarTodos());
        model.addAttribute("listaFormaPagamento" , formaPagamentoService.listarTodos());
        model.addAttribute("listaItemsPorExtratoJson", itemService.pegarItensPorExtratoJson());

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

    @PostMapping("/registraTransacao")
    public String registraTransacaoDetalhada(Extrato extratoForm, @RequestParam String listaItensJson, HttpSession session) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        ObjectMapper mapper = new ObjectMapper();
        Item[] itens = mapper.readValue(listaItensJson, Item[].class);

        extratoService.registraTransacao(extratoForm, usuario);
        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        for (Item item : itens) {
            item.setId_extrato(extratoForm.getId_extrato());
            itemService.salvar(item);
        }

        return "redirect:/admin/extrato";
    }

}
