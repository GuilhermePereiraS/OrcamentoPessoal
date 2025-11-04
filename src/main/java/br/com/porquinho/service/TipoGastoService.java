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

    public List<TipoGasto> listarTodos(Integer idUsuario) {
        return tipoGastoRepository.listarTodos(idUsuario);
    }

    public List<TipoGasto> listarTiposGastoPadrao() {
        return tipoGastoRepository.pegarTiposDeGastoPadrao();
    }
}
