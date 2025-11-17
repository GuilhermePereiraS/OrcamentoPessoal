package br.com.porquinho.service;

import br.com.porquinho.model.Orcamento;
import br.com.porquinho.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;

    OrcamentoService(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    public boolean temOrcamentoNoMesAtual(int idUsuario) {
        return orcamentoRepository.temOrcamentoNoMes(LocalDate.now().getMonth().getValue(), idUsuario);
    }

    public void criaOrcamentoDoMesPadrao(int idUsuario) {
        orcamentoRepository.salvar(LocalDate.now().getMonth().getValue(), LocalDate.now().getYear(), new BigDecimal("1200.00"), idUsuario);
    }

    public Orcamento pegaOrcamentoAtual(int idUsuario) {
        int mesAtual = LocalDate.now().getMonth().getValue();
        int anoAtual = LocalDate.now().getYear();
        return orcamentoRepository.pegaOrcamentoAtual(idUsuario, mesAtual, anoAtual);
    }

    public void atualizar(int idOrcamento, BigDecimal limiteMensal) {
        orcamentoRepository.atualizar(idOrcamento, limiteMensal);
    }

    public Orcamento pegaOrcamentoPorId(int idOrcamento) {
        return orcamentoRepository.pegaOrcamentoPorId(idOrcamento);
    }

}
