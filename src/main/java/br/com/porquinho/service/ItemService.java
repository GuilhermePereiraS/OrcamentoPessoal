package br.com.porquinho.service;

import br.com.porquinho.model.Item;
import br.com.porquinho.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void salvar(Item item){
        itemRepository.salvar(item.getId_extrato(), item.getNome(), item.getVl_unitario(), item.getVl_total(), item.getQuantidade());
    }
}
