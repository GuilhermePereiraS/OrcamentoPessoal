package br.com.porquinho.repository;

import br.com.porquinho.model.FormaPagamento;
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

    public List<TipoGasto> listarTodos(Integer idUsuario) {
        try {
            //alterar pra pegar o tipo gasto por uma relação de usuario -> orçamento até tipo gasto
            String sql =
                    "SELECT tipo_gasto.id_tipo_gasto, tipo_gasto.descricao " +
                    "FROM tipo_gasto, usuario, extrato " +
                            "WHERE usuario.id_usuario = ? " +
                            "AND extrato.id_usuario = usuario.id_usuario " +
                            "AND extrato.id_tipo_gasto = tipo_gasto.id_tipo_gasto " +
                    "ORDER BY tipo_gasto.descricao";
            return template.query(sql, new BeanPropertyRowMapper<>(TipoGasto.class),  idUsuario);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TipoGasto> pegarTiposDeGastoPadrao() {
        try {
            String sql =
                    "SELECT * FROM tipo_gasto " +
                            "WHERE tipo_gasto.id_tipo_gasto IN (1,2,3)";
            return template.query(sql, new BeanPropertyRowMapper<>(TipoGasto.class));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
