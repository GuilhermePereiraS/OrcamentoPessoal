package br.com.porquinho.service;

import br.com.porquinho.dto.TransacaoDTO;
import br.com.porquinho.model.Extrato;
import org.springframework.stereotype.Service;
import br.com.porquinho.model.Extrato.tipoTransacao;

@Service
public class ExtratoService {

    public void registraTransacao(TransacaoDTO transacaoDTO) {
        if (transacaoDTO.getTipoTransacao().equals(tipoTransacao.ENTRADA.getOperacao())) {
            Extrato extrato = new Extrato();
            extrato.setTp_transacao(tipoTransacao.ENTRADA.getOperacao());
        }
    }

}
