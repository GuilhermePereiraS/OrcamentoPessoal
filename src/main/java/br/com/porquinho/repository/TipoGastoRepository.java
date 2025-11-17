package br.com.porquinho.repository;

import br.com.porquinho.model.TipoGasto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoGastoRepository {

    private final JdbcTemplate template;

    public TipoGastoRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    public List<TipoGasto> listarTodos() {
        try {
            String sql =
                    "SELECT * FROM tipo_gasto ORDER BY descricao";
            return template.query(sql, new BeanPropertyRowMapper<>(TipoGasto.class));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TipoGasto> pegarTiposDeGastoPadrao() {
        try {
            String sql =
                    "SELECT * FROM tipo_gasto WHERE tipo_gasto.id_tipo_gasto IN (1,2,3)";
            return template.query(sql, new BeanPropertyRowMapper<>(TipoGasto.class));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void salvar(String descricao) {
        try {
            String sql = "INSERT INTO tipo_gasto (descricao) VALUES (?)";
            template.update(sql, descricao);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
