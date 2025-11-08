package br.com.porquinho.repository;

import br.com.porquinho.model.Orcamento;
import br.com.porquinho.model.Usuario;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public class OrcamentoRepository {

    private final JdbcTemplate template;

    public OrcamentoRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void salvar(Orcamento orcamento) {

    }

    public boolean temOrcamentoNoMes(int mes) {
        String sql = "SELECT COUNT(*) FROM Orcamento orc WHERE orc.mes = ?";
        Integer quantidade =  template.queryForObject(sql, Integer.class, mes);
        return quantidade > 0;
    }

}
