package br.com.porquinho.service;

import br.com.porquinho.model.OrcamentoTipoGasto;
import br.com.porquinho.repository.OrcamentoTipoGastoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrcamentoTipoGastoService {

    private final OrcamentoTipoGastoRepository orcamentoTipoGastoRepository;

    public OrcamentoTipoGastoService(OrcamentoTipoGastoRepository orcamentoTipoGastoRepository) {
        this.orcamentoTipoGastoRepository = orcamentoTipoGastoRepository;
    }

    public void criaOrcamentoTipoGastoPadrao(int idOrcamento) {
        orcamentoTipoGastoRepository.salvar(idOrcamento, 1, new BigDecimal("100.00"));
        orcamentoTipoGastoRepository.salvar(idOrcamento, 2, new BigDecimal("100.00"));
        orcamentoTipoGastoRepository.salvar(idOrcamento, 3, new BigDecimal("100.00"));
    }

    public List<OrcamentoTipoGasto> listarTodos(int idOrcamento) {
       return orcamentoTipoGastoRepository.listarTodos(idOrcamento);
    }

    public Integer quantidadeOrcamentosTipoGasto(int idOrcamento) {
        return orcamentoTipoGastoRepository.quantidadeOrcamentosTipoGasto(idOrcamento);
    }

    public void atualizar(Integer idOrcamento, Integer idTipoGasto, BigDecimal limite) {
        orcamentoTipoGastoRepository.atualizar(idOrcamento, idTipoGasto, limite);
    }


    public void salvar(Integer idOrcamento, Integer idTipoGasto, BigDecimal limite) {
        orcamentoTipoGastoRepository.salvar(idOrcamento, idTipoGasto, limite);
    }
}
