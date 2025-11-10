package br.com.porquinho.service;

import br.com.porquinho.model.TipoGasto;
import br.com.porquinho.repository.TipoGastoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoGastoService {

    private final TipoGastoRepository tipoGastoRepository;

    public TipoGastoService(TipoGastoRepository tipoGastoRepository) {
        this.tipoGastoRepository = tipoGastoRepository;
    }

    public List<TipoGasto> listarTodos() {
        return tipoGastoRepository.listarTodos();
    }

    public List<TipoGasto> listarTiposGastoPadrao() {
        return tipoGastoRepository.pegarTiposDeGastoPadrao();
    }

    public void salvar(String descricao) {
        tipoGastoRepository.salvar(descricao);
    }
}
