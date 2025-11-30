package br.com.porquinho.service;

import br.com.porquinho.model.Orcamento;
import br.com.porquinho.repository.OrcamentoRepository;
import br.com.porquinho.repository.OrcamentoTipoGastoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final OrcamentoTipoGastoRepository orcamentoTipoGastoRepository;

    public OrcamentoService(OrcamentoRepository orcamentoRepository, OrcamentoTipoGastoRepository orcamentoTipoGastoRepository) {
        this.orcamentoRepository = orcamentoRepository;
        this.orcamentoTipoGastoRepository = orcamentoTipoGastoRepository;
    }

    public BigDecimal pegaSomaDosLimitesTipoGasto(Integer idOrcamento) {
        return orcamentoTipoGastoRepository.pegaSomaDosLimites(idOrcamento);
    }

    public void criaOrcamentoDoMesPadrao(int idUsuario) {
        orcamentoRepository.salvar(LocalDate.now().getMonth().getValue(), LocalDate.now().getYear(), new BigDecimal("1200.00"), idUsuario);
    }

    public Orcamento pegaOrcamentoAtual(int idUsuario) {
        int mesAtual = LocalDate.now().getMonth().getValue();
        int anoAtual = LocalDate.now().getYear();
        return orcamentoRepository.pegaOrcamentoAtual(idUsuario, mesAtual, anoAtual);
    }

    public void atualizar(int idOrcamento, BigDecimal limiteMensal) throws Exception {
        BigDecimal somaDosLimitesTipoGasto = pegaSomaDosLimitesTipoGasto(idOrcamento);

        if (somaDosLimitesTipoGasto.compareTo(limiteMensal) > 0) {
            throw new Exception("Valor do orçamento mensal é menor que a soma dos sub-orçamentos");
        }
        orcamentoRepository.atualizar(idOrcamento, limiteMensal);
    }

    public Orcamento pegaOrcamentoPorId(int idOrcamento) {
        return orcamentoRepository.pegaOrcamentoPorId(idOrcamento);
    }

}
