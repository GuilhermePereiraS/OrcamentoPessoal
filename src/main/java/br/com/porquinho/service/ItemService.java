package br.com.porquinho.service;

import br.com.porquinho.model.Item;
import br.com.porquinho.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public Map<Integer, String> pegarItensPorExtratoJson() throws JsonProcessingException {
        return itemRepository.pegarTodosItensPorExtrato();
    }
}
