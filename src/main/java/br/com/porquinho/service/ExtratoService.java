package br.com.porquinho.service;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Item;
import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.ExtratoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import br.com.porquinho.model.Extrato.tipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
public class ExtratoService {

    private final ExtratoRepository extratoRepository;
    private final UsuarioService usuarioService;
    private final ItemService itemService;

    ExtratoService(ExtratoRepository extratoRepository, UsuarioService usuarioService, ItemService itemService) {
        this.extratoRepository = extratoRepository;
        this.usuarioService = usuarioService;
        this.itemService = itemService;
    }


    public void registraTransacao(Extrato extratoForm) throws Exception {
        if (extratoForm.getVl_transacao() == null || extratoForm.getVl_transacao().compareTo(BigDecimal.ZERO) == 0) {
            throw new Exception("Transação não registrada, valor da transação é zero!");
        }

        Usuario usuario = usuarioService.encontraPorId(extratoForm.getId_usuario());
        Extrato extrato = new Extrato();

        extrato.setId_usuario(extratoForm.getId_usuario());
        Integer idExtrato;

        extrato.setDescricao(extratoForm.getDescricao());
        extrato.setVl_transacao(extratoForm.getVl_transacao());
        extrato.setDt_transacao(extratoForm.getDt_transacao());

        if (extratoForm.getTp_transacao().equals(tipoTransacao.ENTRADA.getOperacao())) {
            System.out.println("ENTRADA");
            extrato.setTp_transacao(tipoTransacao.ENTRADA.getOperacao());

            idExtrato = extratoRepository.salvarEntrada(extrato);
        } else {
            extrato.setTp_transacao(tipoTransacao.SAIDA.getOperacao());
            extrato.setId_tipo_gasto(extratoForm.getId_tipo_gasto());
            extrato.setId_forma_pgmt(extratoForm.getId_forma_pgmt());
            if (usuario.getSaldo() == null || extratoForm.getVl_transacao().compareTo(usuario.getSaldo()) > 0) {
                throw new Exception("Saldo insuficiente");
            }
            idExtrato = extratoRepository.salvarSaida(extrato);
        }

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

    public void atualiza(Extrato extrato) {
        if (extrato.getTp_transacao().equals(tipoTransacao.ENTRADA.getOperacao())) {
            extratoRepository.atualizaEntrada(extrato);
        } else {
            extratoRepository.atualizaSaida(extrato);
        }
    }

    public void excluir(Extrato extratoForm) {
        itemService.excluirTodosVinculados(extratoForm.getId_extrato());
        extratoRepository.excluir(extratoForm.getId_extrato());
    }

    public boolean existeNoBanco(Extrato extratoForm) {
        return extratoRepository.existeExtratoNoBanco(extratoForm.getId_extrato());
    }

    public void atualizaExtratoDetalhado(Extrato extratoForm, String listaItensJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(listaItensJson);
        Item[] itens = mapper.readValue(listaItensJson, Item[].class);
        List<Item> itemsNoBanco = itemService.pegaItensPorExtrato(extratoForm.getId_extrato());

        for (Item itemBanco : itemsNoBanco) {
            boolean existeNoFront = false;
            for (Item itemFront : itens) {
                if (itemFront.getId_item() != null && itemFront.getId_item().equals(itemBanco.getId_item())) {
                    existeNoFront = true;
                    break;
                }
            }
            if (!existeNoFront) {
                itemService.excluir(itemBanco.getId_item());
            }
        }

        if (itens.length == 0) {
            excluir(extratoForm);
            return;
        }

        atualiza(extratoForm);

        for (Item item : itens) {
            if (item.getId_item() == null) {
                item.setId_extrato(extratoForm.getId_extrato());
                itemService.salvar(item);
                continue;
            }
            itemService.atualizar(item);
        }
    }
}
