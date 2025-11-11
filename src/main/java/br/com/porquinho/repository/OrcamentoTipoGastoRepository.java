package br.com.porquinho.repository;

import br.com.porquinho.model.OrcamentoTipoGasto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrcamentoTipoGastoRepository {
    private final JdbcTemplate template;

    public OrcamentoTipoGastoRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void salvar(int idOrcamento, int idTipoGasto, BigDecimal limite) {
        try {
            String sql = "INSERT INTO orcamento_tipo_gasto (id_orcamento, id_tipo_gasto, limite) VALUES (?, ?, ?)";
            template.update(sql, idOrcamento, idTipoGasto, limite);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atualizar(int idOrcamento, int idTipoGasto, BigDecimal limite) {
        try {
            String sql = "UPDATE orcamento_tipo_gasto SET limite = ? WHERE  id_tipo_gasto = ? AND  id_orcamento = ?";
            System.out.println("id tipo ORC" + idOrcamento);
            template.update(sql,limite, idTipoGasto, idOrcamento);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<OrcamentoTipoGasto> listarTodos(int idOrcamento) {
        String sql = "SELECT * FROM orcamento_tipo_gasto WHERE id_orcamento = ? ORDER BY id_tipo_gasto";
        return template.query(sql, new BeanPropertyRowMapper<>(OrcamentoTipoGasto.class), idOrcamento);
    }

    public Integer quantidadeOrcamentosTipoGasto(int idOrcamento) {
        String sql = "SELECT COUNT(*) FROM orcamento_tipo_gasto WHERE id_orcamento = ? ";
        return template.queryForObject(sql, Integer.class, idOrcamento);
    }

    public void excluir(int idOrcamento, int idTipoGasto) {
        String sql = "DELETE FROM orcamento_tipo_gasto WHERE id_orcamento = ? AND id_tipo_gasto = ?";
        template.update(sql, idOrcamento, idTipoGasto);
    }
}
