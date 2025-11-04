package br.com.porquinho.controller.admin;

import br.com.porquinho.model.TipoGasto;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.service.TipoGastoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TipoGastoController {

    private final TipoGastoService tipoGastoService;

    public TipoGastoController(TipoGastoService tipoGastoService) {
        this.tipoGastoService = tipoGastoService;
    }

    @GetMapping("/pegaListaTipoGasto")
    @ResponseBody
    public List<TipoGasto> pegaListaTipoGasto(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        return tipoGastoService.listarTiposGastoPadrao();
    }


}
