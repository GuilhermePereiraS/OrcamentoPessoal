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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static br.com.porquinho.model.Extrato.tipoTransacao.ENTRADA;
import static br.com.porquinho.util.PorquinhoUtils.*;

@Controller
@RequestMapping("/admin/extrato")
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

    @GetMapping
    public String extrato(Model model, HttpSession session) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        model.addAttribute("listaExtrato", extratoService.listarTodos(usuario.getId_usuario()));
        model.addAttribute("listaTipoGasto", tipoGastoService.listarTodos());
        model.addAttribute("listaFormaPagamento" , formaPagamentoService.listarTodos());
        model.addAttribute("listaItemsPorExtratoJson", itemService.pegarTodosItensPorExtratoJson());

        return "pages/admin/extrato/index";
    }

    @PostMapping
    public String registraTransacao(Extrato extratoForm, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        try {
            extratoForm.setId_usuario(usuario.getId_usuario());
            extratoService.registraTransacao(extratoForm);
            criaMensagemSucesso(redirectAttributes, "Extrato salvo com sucesso!");
        } catch (Exception e) {
            criaMensagemErro(redirectAttributes, e.getMessage());
        }
        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        // retorna a pagina de onde foi enviado o formulario
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

    @PostMapping("/acaoExtrato")
    public String acaoExtrato(Extrato extratoForm, @RequestParam String listaItensJson, @RequestParam String acao, HttpSession session, HttpServletRequest request) {
        return switch (acao) {
            case "gravar" -> "forward:/admin/extrato/registraTransacao";
            case "atualizar" -> "forward:/admin/extrato/atualizar";
            case "excluir" -> "forward:/admin/extrato/excluir";
            default -> "redirect:/admin/extrato";
        };
    }

        @PostMapping("/excluir")
        public String excluir(Extrato extratoForm, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

            extratoService.excluir(extratoForm);
            criaMensagemSucesso(redirectAttributes, "Extrato excluido com sucesso!");

            session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));
            return "redirect:/admin/extrato";
        }

    @PostMapping("/registraTransacao")
    public String registraTransacaoDetalhada(Extrato extratoForm, @RequestParam String listaItensJson, HttpSession session, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        ObjectMapper mapper = new ObjectMapper();
        Item[] itens = mapper.readValue(listaItensJson, Item[].class);

        try {
            extratoForm.setId_usuario(usuario.getId_usuario());
            extratoService.registraTransacao(extratoForm);

            for (Item item : itens) {
                item.setId_extrato(extratoForm.getId_extrato());
                itemService.salvar(item);
            }
            criaMensagemSucesso(redirectAttributes, "Extrato salvo com sucesso!");
        } catch (Exception e) {
            criaMensagemErro(redirectAttributes,e.getMessage());
        }
        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        return "redirect:/admin/extrato";
    }

    @PostMapping("/atualizar")
    public String atualizarExtrato(Extrato extratoForm, String listaItensJson, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        List<Item> listaItensNoBanco = itemService.pegaItensPorExtrato(extratoForm.getId_extrato());

        if (extratoForm.getTp_transacao().equals(ENTRADA.getOperacao()) || listaItensNoBanco.isEmpty() ) {
            extratoService.atualiza(extratoForm);
        } else {
            extratoService.atualizaExtratoDetalhado(extratoForm, listaItensJson);
        }

        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        if (!extratoService.existeNoBanco(extratoForm)) {
            criaMensagemAlerta(redirectAttributes, "Extrato excluido pois não há itens!");
        } else {
            criaMensagemSucesso(redirectAttributes, "Extrato atualizado com sucesso!");
        }
        return "redirect:/admin/extrato";
    }

}
