package br.com.porquinho.service;

import br.com.porquinho.repository.OrcamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrcamentoService {

    private OrcamentoRepository orcamentoRepository;

    OrcamentoService(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    public boolean temOrcamentoNoMesAtual() {
        return orcamentoRepository.temOrcamentoNoMes(LocalDate.now().getMonth().getValue());
    }

    public void criaExtratoDoMesPadrao() {
    }
}
