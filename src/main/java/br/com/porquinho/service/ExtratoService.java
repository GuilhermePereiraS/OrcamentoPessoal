package br.com.porquinho.service;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.ExtratoRepository;
import org.springframework.stereotype.Service;
import br.com.porquinho.model.Extrato.tipoTransacao;

@Service
public class ExtratoService {

    ExtratoRepository extratoRepository;

    ExtratoService(ExtratoRepository extratoRepository) {
        this.extratoRepository = extratoRepository;
    }

    public void registraTransacao(Extrato extratoForm, Usuario usuario) {
        Extrato extrato = new Extrato();


        extrato.setDescricao(extratoForm.getDescricao());
        extrato.setVl_transacao(extratoForm.getVl_transacao());
        extrato.setDt_transacao(extratoForm.getDt_transacao());
        extrato.setId_usuario(usuario.getId_usuario());

        if (extratoForm.getTp_transacao().equals(tipoTransacao.ENTRADA.getOperacao())) {
            extrato.setTp_transacao(tipoTransacao.ENTRADA.getOperacao());
            extratoRepository.salvarEntrada(extrato);
        } else {
            extrato.setTp_transacao(tipoTransacao.SAIDA.getOperacao());

            if (extratoForm.getVl_transacao().compareTo(usuario.getSaldo()) > 0) {
                // alguma mensagem de erro
                return;
            }
            extratoRepository.salvarSaida(extrato);
        }
    }

}
