package br.com.porquinho.repository;

import br.com.porquinho.model.Extrato;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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

    public HashMap<Integer, BigDecimal> pegarGastoPorTipoGasto(Integer idUsuario, int mes, int ano) {
        String sql = "SELECT id_tipo_gasto, SUM(ext.vl_transacao) as  vl_transacao " +
                "FROM extrato ext " +
                "WHERE id_usuario = ? " +
                "AND ext.tp_transacao = 'saida' " +
                "AND EXTRACT(MONTH FROM dt_transacao) = ? " +
                "AND EXTRACT(YEAR FROM dt_transacao) = ? " +
                "GROUP BY id_tipo_gasto";
        return template.query(sql,rs -> {
            HashMap<Integer,BigDecimal> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getInt("id_tipo_gasto"), rs.getBigDecimal("vl_transacao"));
            }
            return map;
        }, idUsuario, mes, ano);
    }




}
