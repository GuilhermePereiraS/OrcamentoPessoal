package br.com.porquinho.service;

import br.com.porquinho.model.Orcamento;
import br.com.porquinho.model.OrcamentoTipoGasto;
import br.com.porquinho.repository.OrcamentoTipoGastoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrcamentoTipoGastoService {

    private final OrcamentoTipoGastoRepository orcamentoTipoGastoRepository;
    private final OrcamentoService orcamentoService;

    public OrcamentoTipoGastoService(OrcamentoTipoGastoRepository orcamentoTipoGastoRepository, OrcamentoService orcamentoService) {
        this.orcamentoTipoGastoRepository = orcamentoTipoGastoRepository;
        this.orcamentoService = orcamentoService;
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

        ajustarLimiteMensalSeNecessario(idOrcamento, limite);
    }

    public BigDecimal pegaSomaDosLimites(Integer idOrcamento) {
        return orcamentoTipoGastoRepository.pegaSomaDosLimites(idOrcamento);
    }

    public void salvar(int idOrcamento, Integer idTipoGasto, BigDecimal limite) {
        orcamentoTipoGastoRepository.salvar(idOrcamento, idTipoGasto, limite);

        ajustarLimiteMensalSeNecessario(idOrcamento, limite);
    }

    public void excluir(Integer idOrcamento, Integer idTipoGasto) {
        orcamentoTipoGastoRepository.excluir(idOrcamento, idTipoGasto);
    }

    private void ajustarLimiteMensalSeNecessario(int idOrcamento, BigDecimal limite) {
        Orcamento orcamentoDoMes = orcamentoService.pegaOrcamentoPorId(idOrcamento);

        BigDecimal valorSomaDosLimitesTipoGasto = pegaSomaDosLimites(orcamentoDoMes.getId_orcamento());
        BigDecimal limiteOrcamentoDoMes = orcamentoDoMes.getLimite_mensal();

        if (valorSomaDosLimitesTipoGasto.compareTo(limiteOrcamentoDoMes) > 0) {
            BigDecimal limiteOrcamentoMensalNovo = limiteOrcamentoDoMes.add(limite);
            orcamentoService.atualizar(orcamentoDoMes.getId_orcamento(), limiteOrcamentoMensalNovo);
        }
    }
}
