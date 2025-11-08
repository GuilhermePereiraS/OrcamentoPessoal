package br.com.porquinho.util;

import br.com.porquinho.service.OrcamentoService;
import org.springframework.boot.CommandLineRunner;

public class OrcamentoIniciador implements CommandLineRunner {

    private OrcamentoService orcamentoService;
    public OrcamentoIniciador(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @Override
    public void run(String... args) {
        if (!orcamentoService.temOrcamentoNoMesAtual()) {
            orcamentoService.criaExtratoDoMesPadrao();
        }
    }
}
