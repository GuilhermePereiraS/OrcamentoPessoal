package br.com.porquinho.service;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Porquinho;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.PorquinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.porquinho.model.Extrato.tipoTransacao.ENTRADA;
import static br.com.porquinho.model.Extrato.tipoTransacao.SAIDA;

@Service
public class PorquinhoService {

    private final PorquinhoRepository porquinhoRepository;

    private final ExtratoService extratoService;

    private final UsuarioService usuarioService;

    public PorquinhoService(PorquinhoRepository porquinhoRepository, ExtratoService extratoService, UsuarioService usuarioService) {
        this.porquinhoRepository = porquinhoRepository;
        this.extratoService = extratoService;
        this.usuarioService = usuarioService;
    }

    public void salvar(Porquinho porquinho) throws Exception {
        Usuario usuario = usuarioService.encontraPorId(porquinho.getId_usuario());

        if (porquinho.getVl_alcancado() == null) {
            porquinho.setVl_alcancado(BigDecimal.ZERO);
        }

            if (usuario.getSaldo() == null || porquinho.getVl_alcancado().compareTo(usuario.getSaldo()) > 0) {
                throw new Exception("Saldo insuficiente");
            }

            if (porquinho.getVl_alcancado().compareTo(porquinho.getVl_necessario()) > 0) {
                throw new Exception("Valor alcançado não pode ser maior que o necessário");
            }


        porquinhoRepository.salvar(porquinho);
        extratoService.registraTransacao(criaExtratoPorquinho(porquinho));
    }

    public void atualizar(Porquinho porquinho) throws Exception {
        Usuario usuario = usuarioService.encontraPorId(porquinho.getId_usuario());
        Porquinho porquinhoNoBanco = encontraPorId(porquinho.getId_porquinho());
        BigDecimal valorTransacao;
        Extrato.tipoTransacao tipoTransacao;

        if (porquinho.getVl_alcancado().compareTo(porquinho.getVl_necessario()) > 0) {
            throw new Exception("Valor alcançado não pode ser maior que o necessário");
        }

        //verifica se o valor alcançado NA APLICAÇÃO é maior que o valor no BANCO
        if (porquinho.getVl_alcancado().compareTo(porquinhoNoBanco.getVl_alcancado()) > 0) {
            tipoTransacao = SAIDA;
            valorTransacao = porquinho.getVl_alcancado().subtract(porquinhoNoBanco.getVl_alcancado());
        } else if (porquinhoNoBanco.getVl_alcancado().compareTo(porquinho.getVl_alcancado()) > 0) {
            tipoTransacao = ENTRADA;
            valorTransacao = porquinhoNoBanco.getVl_alcancado().subtract(porquinho.getVl_alcancado());
        } else {
            throw new Exception("Valor é o mesmo, não houve alteração");
        }

        if (tipoTransacao == SAIDA && usuario.getSaldo() == null || valorTransacao.compareTo(usuario.getSaldo()) > 0) {
            throw new Exception("Saldo insuficiente");
        }

        Extrato extrato = criaExtratoPorquinho(porquinho, valorTransacao, tipoTransacao);
        porquinhoRepository.atualizar(porquinho);
        extratoService.registraTransacao(extrato);
    }

    public void excluir(Porquinho porquinho) throws Exception {
        porquinhoRepository.excluir(porquinho);
        Extrato extrato = criaExtratoPorquinho(porquinho, porquinho.getVl_alcancado(), ENTRADA);
        extratoService.registraTransacao(extrato);
    }

    public List<Porquinho> listarTodos(Integer idUsuario) {
        return porquinhoRepository.listarTodos(idUsuario);
    }

    public Extrato criaExtratoPorquinho(Porquinho porquinho) {
        Extrato extrato = new Extrato();
        extrato.setId_usuario(porquinho.getId_usuario());
        extrato.setId_forma_pgmt(2);
        extrato.setId_tipo_gasto(15);
        extrato.setDescricao("Porquinho");
        extrato.setVl_transacao(porquinho.getVl_alcancado());
        extrato.setDt_transacao(LocalDate.now());
        extrato.setTp_transacao(SAIDA.getOperacao());

        return extrato;
    }

    public Extrato criaExtratoPorquinho(Porquinho porquinho, BigDecimal valorTransacao, Extrato.tipoTransacao tipoTransacao) {

        Extrato extrato = new Extrato();
        extrato.setId_usuario(porquinho.getId_usuario());
        extrato.setId_forma_pgmt(2);
        extrato.setId_tipo_gasto(15);
        extrato.setDescricao("Porquinho");
        extrato.setVl_transacao(valorTransacao);
        extrato.setDt_transacao(LocalDate.now());
        extrato.setTp_transacao(tipoTransacao.getOperacao());

        return extrato;
    }

    public Porquinho encontraPorId(Integer id) {
        return porquinhoRepository.encontraPorId(id);
    }

}
