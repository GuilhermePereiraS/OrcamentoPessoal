package br.com.porquinho.repository;

import br.com.porquinho.model.FormaPagamento;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormaPagamentoRepository {

    private final JdbcTemplate template;

    public FormaPagamentoRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    public List<FormaPagamento> listarTodos() {
        try {
            String sql = "SELECT id_forma_pgmt, descricao FROM forma_pgmt ORDER BY forma_pgmt.descricao";
            return template.query(sql, new BeanPropertyRowMapper<>(FormaPagamento.class));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
