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

import java.util.List;

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
    public String registraTransacao(Extrato extratoForm, HttpSession session, HttpServletRequest request) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        extratoForm.setId_usuario(usuario.getId_usuario());
        extratoService.registraTransacao(extratoForm);

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
        public String excluir(Extrato extratoForm, HttpSession session, HttpServletRequest request) {
            extratoService.excluir(extratoForm);
            return "redirect:/admin/extrato";
        }

    @PostMapping("/registraTransacao")
    public String registraTransacaoDetalhada(Extrato extratoForm, @RequestParam String listaItensJson, HttpSession session) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        ObjectMapper mapper = new ObjectMapper();
        Item[] itens = mapper.readValue(listaItensJson, Item[].class);


        extratoForm.setId_usuario(usuario.getId_usuario());
        extratoService.registraTransacao(extratoForm);
        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        for (Item item : itens) {
            item.setId_extrato(extratoForm.getId_extrato());
            itemService.salvar(item);
        }

        return "redirect:/admin/extrato";
    }

    @PostMapping("/atualizar")
    public String atualizarExtrato(Extrato extratoForm, String listaItensJson, HttpSession session, HttpServletRequest request) throws JsonProcessingException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(listaItensJson);
        Item[] itens = mapper.readValue(listaItensJson, Item[].class);
        List<Item> itemsNoBanco = itemService.pegaItensPorExtrato(extratoForm.getId_extrato());

        for (Item itemBanco : itemsNoBanco) {
            boolean existeNoFront = false;
            for (Item itemFront : itens) {
                if (itemFront.getId_item() != null && itemFront.getId_item().equals(itemBanco.getId_item())) {
                    existeNoFront = true;
                    break;
                }
            }
            if (!existeNoFront) {
                itemService.excluir(itemBanco.getId_item());
            }
        }

        System.out.println("listaItensJson: " + extratoForm.toString());

        extratoService.atualiza(extratoForm);
        session.setAttribute("usuarioLogado", usuarioService.encontraPorLogin(usuario.getLogin()));

        for (Item item : itens) {
            if (item.getId_item() == null) {
                item.setId_extrato(extratoForm.getId_extrato());
                itemService.salvar(item);
                continue;
            }

            itemService.atualizar(item);
        }

        return "redirect:/admin/extrato";
    }

}
