package br.com.porquinho.controller.admin;

import br.com.porquinho.dto.OrcamentoDTO;
import br.com.porquinho.model.Orcamento;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.OrcamentoRepository;
import br.com.porquinho.repository.OrcamentoTipoGastoRepository;
import br.com.porquinho.service.ExtratoService;
import br.com.porquinho.service.OrcamentoService;
import br.com.porquinho.service.OrcamentoTipoGastoService;
import br.com.porquinho.service.TipoGastoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;
    private final OrcamentoTipoGastoService orcamentoTipoGastoService;
    private final TipoGastoService tipoGastoService;
    private final ExtratoService extratoService;
    private final OrcamentoTipoGastoRepository orcamentoTipoGastoRepository;
    private final OrcamentoRepository orcamentoRepository;

    public OrcamentoController(OrcamentoService orcamentoService, OrcamentoTipoGastoService orcamentoTipoGastoService, TipoGastoService tipoGastoService, ExtratoService extratoService, OrcamentoTipoGastoRepository orcamentoTipoGastoRepository, OrcamentoRepository orcamentoRepository) {
        this.orcamentoService = orcamentoService;
        this.orcamentoTipoGastoService = orcamentoTipoGastoService;
        this.tipoGastoService = tipoGastoService;
        this.extratoService = extratoService;
        this.orcamentoTipoGastoRepository = orcamentoTipoGastoRepository;
        this.orcamentoRepository = orcamentoRepository;
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

    @PostMapping("/orcamento/acao")
    public String acaoOrcamento(@RequestParam String acao, OrcamentoDTO orcamentoDTO) {
        return switch (acao) {
            case "excluir" -> "forward:/admin/orcamento/excluir";
            case "editar" -> "forward:/admin/orcamento/atualizar";
            case "gravar" -> "forward:/admin/orcamento/salvar";
            default -> "redirect:/admin/orcamento";
        };
    }

    @PostMapping("/orcamento/atualizar")
    public String atualizarOrcamento(OrcamentoDTO orcamentoDTO, HttpSession session) {
        if (orcamentoDTO.getId_tipo_gasto() != null) {
            orcamentoTipoGastoRepository.atualizar(orcamentoDTO.getId_orcamento(), orcamentoDTO.getId_tipo_gasto(), orcamentoDTO.getLimite());
        } else {
            orcamentoRepository.atualizar(orcamentoDTO.getId_orcamento(), orcamentoDTO.getLimite());
        }
        return "redirect:/admin/orcamento";
    }

    @PostMapping("/orcamento/excluir")
    public String excluirOrcamento(OrcamentoDTO orcamentoDTO, HttpSession session) {
        if (orcamentoDTO.getId_tipo_gasto() != null) {
            orcamentoTipoGastoRepository.excluir(orcamentoDTO.getId_orcamento(), orcamentoDTO.getId_tipo_gasto());
        }
        return "redirect:/admin/orcamento";
    }


    @PostMapping("/orcamento/salvar")
    public String salvarOrcamento(OrcamentoDTO orcamentoDTO, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Orcamento orcamentoAtual = orcamentoService.pegaOrcamentoAtual(usuarioLogado.getId_usuario());

        orcamentoTipoGastoService.salvar(orcamentoAtual.getId_orcamento(), orcamentoDTO.getId_tipo_gasto(), orcamentoDTO.getLimite());

        return "redirect:/admin/orcamento";
    }

}
