package br.com.porquinho.service;

import br.com.porquinho.model.Item;
import br.com.porquinho.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void salvar(Item item){
        itemRepository.salvar(item.getId_extrato(), item.getNome(), item.getVl_unitario(), item.getVl_total(), item.getQuantidade());
    }

    public Map<Integer, String> pegarTodosItensPorExtratoJson() throws JsonProcessingException {
        return itemRepository.pegarTodosItensPorExtrato();
    }

    public void atualizar(Item item) {
        itemRepository.atualizar(item);
    }

    public List<Item> pegaItensPorExtrato(int idExtrato){
        return itemRepository.pegaItemsPorExtrato(idExtrato);
    }

    public void excluir(Integer idItem) {
        itemRepository.excluir(idItem);
    }

    public void excluirTodosVinculados(Integer idExtrato) {
        itemRepository.excluirTodosVinculados(idExtrato);
    }
}
