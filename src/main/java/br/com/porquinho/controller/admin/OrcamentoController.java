package br.com.porquinho.controller.admin;

import br.com.porquinho.model.Orcamento;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.ExtratoService;
import br.com.porquinho.service.OrcamentoService;
import br.com.porquinho.service.OrcamentoTipoGastoService;
import br.com.porquinho.service.TipoGastoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;
    private final OrcamentoTipoGastoService orcamentoTipoGastoService;
    private final TipoGastoService tipoGastoService;
    private final ExtratoService extratoService;

    public OrcamentoController(OrcamentoService orcamentoService, OrcamentoTipoGastoService orcamentoTipoGastoService, TipoGastoService tipoGastoService, ExtratoService extratoService) {
        this.orcamentoService = orcamentoService;
        this.orcamentoTipoGastoService = orcamentoTipoGastoService;
        this.tipoGastoService = tipoGastoService;
        this.extratoService = extratoService;
    }

    @GetMapping("/orcamento")
    public String index(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Orcamento orcamentoAtual = orcamentoService.pegaOrcamentoAtual(usuarioLogado.getId_usuario());

        model.addAttribute("orcamentoDoMes", orcamentoAtual);
        model.addAttribute("listaOrcamentoTipoGasto", orcamentoTipoGastoService.listarTodos(orcamentoAtual.getId_orcamento()));
        model.addAttribute("listaTipoGasto", tipoGastoService.listarTodos());
        model.addAttribute("gastoDoMes", extratoService.pegarGastoDoMes(usuarioLogado.getId_usuario()));
        System.out.println(extratoService.pegarGastoDoMes(usuarioLogado.getId_usuario()));
        model.addAttribute("gastoPorTipoGasto", extratoService.pegarGastosPorTipoGasto(usuarioLogado.getId_usuario()));

        return "pages/admin/orcamento/orcamento";
    }

}
