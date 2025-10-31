package br.com.porquinho.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExtratoRepository {
    private final JdbcTemplate template;

    public ExtratoRepository(JdbcTemplate template) {
        this.template = template;
    }


}
