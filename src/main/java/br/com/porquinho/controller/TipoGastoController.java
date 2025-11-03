package br.com.porquinho.controller;

import br.com.porquinho.model.TipoGasto;
import br.com.porquinho.repository.TipoGastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TipoGastoController {

    private final TipoGastoRepository tipoGastoRepository;

    public TipoGastoController(TipoGastoRepository tipoGastoRepository) {
        this.tipoGastoRepository = tipoGastoRepository;
    }

    @GetMapping("/pegaListaTipoGasto")
    @ResponseBody
    public List<TipoGasto> dashboard() {
        return tipoGastoRepository.listarTodos();
    }


}
