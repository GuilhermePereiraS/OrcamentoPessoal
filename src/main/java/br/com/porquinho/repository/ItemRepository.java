package br.com.porquinho.repository;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private final JdbcTemplate template;

    public ItemRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void salvar(int id_extrato, String nome, BigDecimal valorUnitario, BigDecimal valorTotal, int quantidade) {
        String sql = "INSERT INTO item (id_extrato, nome, vl_unitario, vl_total, quantidade) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, id_extrato, nome, valorUnitario, valorTotal, quantidade);
    }

    public void atualizar(Item item) {
        String sql = "UPDATE item SET nome = ?, vl_unitario = ?, vl_total = ?, quantidade = ? WHERE id_item = ?";
        template.update(sql, item.getNome(), item.getVl_unitario(), item.getVl_total(), item.getQuantidade(), item.getId_item());
    }

    public HashMap<Integer, String> pegarTodosItensPorExtrato() throws JsonProcessingException {
        String sql = "SELECT * FROM item";
        List<Item> itens = template.query(sql, new BeanPropertyRowMapper<>(Item.class));
        HashMap<Integer, List<Item>> mapItem = new HashMap<>();

        for (Item item : itens) {
            mapItem.computeIfAbsent(item.getId_extrato(), chave -> new ArrayList<>()).add(item);
        }

        HashMap<Integer, String> mapaItemJson = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        for (Map.Entry<Integer, List<Item>> entry : mapItem.entrySet()) {
            String json = mapper.writeValueAsString(entry.getValue());
            mapaItemJson.put(entry.getKey(), json);
        }

        return mapaItemJson;
    }

    public List<Item> pegaItemsPorExtrato(int idExtrato) {
        String sql = "SELECT * FROM item WHERE id_extrato = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Item.class), idExtrato);
    }

    public void excluir(Integer idItem) {
        String sql = "DELETE FROM item WHERE id_item = ?";
        template.update(sql, idItem);
    }
}
