package br.com.porquinho.service;

import br.com.porquinho.dto.GastoPorTipoGasto;
import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.OrcamentoTipoGasto;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.ExtratoRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import br.com.porquinho.model.Extrato.tipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ExtratoService {

    private final ExtratoRepository extratoRepository;
    private final OrcamentoTipoGastoService orcamentoTipoGastoService;

    ExtratoService(ExtratoRepository extratoRepository, OrcamentoTipoGastoService orcamentoTipoGastoService) {
        this.extratoRepository = extratoRepository;
        this.orcamentoTipoGastoService = orcamentoTipoGastoService;
    }


    public void registraTransacao(Extrato extratoForm, Usuario usuario) {
        Extrato extrato = new Extrato();
        Integer idExtrato = null;

        extrato.setDescricao(extratoForm.getDescricao());
        extrato.setVl_transacao(extratoForm.getVl_transacao());
        extrato.setDt_transacao(extratoForm.getDt_transacao());
        extrato.setId_usuario(usuario.getId_usuario());

        if (extratoForm.getTp_transacao().equals(tipoTransacao.ENTRADA.getOperacao())) {
            System.out.println("ENTRADA");
            extrato.setTp_transacao(tipoTransacao.ENTRADA.getOperacao());

            idExtrato = extratoRepository.salvarEntrada(extrato);
        } else {
            extrato.setTp_transacao(tipoTransacao.SAIDA.getOperacao());
            extrato.setId_tipo_gasto(extratoForm.getId_tipo_gasto());
            extrato.setId_forma_pgmt(extratoForm.getId_forma_pgmt());
            System.out.println("saida");
            if (usuario.getSaldo() == null || extratoForm.getVl_transacao().compareTo(usuario.getSaldo()) > 0) {
                // alguma mensagem de erro
                return;
            }
            idExtrato = extratoRepository.salvarSaida(extrato);
        }

        System.out.println("idExtrato: " + idExtrato);
        extratoForm.setId_extrato(idExtrato);
    }

    public List<Extrato> listarTodos(Integer idUsuario) {
        return extratoRepository.listarTodos(idUsuario);
    }

    public HashMap<Integer, BigDecimal> pegarGastosPorTipoGasto(Integer idUsuario) {
       return extratoRepository.pegarGastoPorTipoGasto(idUsuario, LocalDate.now().getMonth().getValue(), LocalDate.now().getYear());
    }

    public BigDecimal pegarGastoDoMes(Integer idUsuario) {
        return extratoRepository.pegarGastoDoMes(idUsuario, LocalDate.now().getMonth().getValue(), LocalDate.now().getYear());
    }

}
