package br.com.porquinho.repository;

import br.com.porquinho.model.Orcamento;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class OrcamentoRepository {

    private final JdbcTemplate template;

    public OrcamentoRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void salvar(Orcamento orcamento) {

    }

    public boolean temOrcamentoNoMes(int mes, int idUsuario) {
        String sql = "SELECT COUNT(*) FROM Orcamento orc WHERE orc.mes = ? AND orc.id_usuario = ?";
        Integer quantidade =  template.queryForObject(sql, Integer.class, mes, idUsuario);
        return quantidade > 0;
    }

    public void salvar(int mesAtual, int anoAtual, BigDecimal limiteMensal, int id_usuario) {
        String sql = "INSERT INTO Orcamento (mes, ano, limite_mensal, id_usuario) VALUES (?, ?, ?, ?)";
        template.update(sql, mesAtual, anoAtual, limiteMensal, id_usuario);
    }

    public Orcamento pegaOrcamentoAtual(int idUsuario, int mesAtual, int anoAtual) {
        String sql = "SELECT * FROM Orcamento orc WHERE id_usuario = ? AND orc.mes = ? AND orc.ano = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Orcamento.class), idUsuario, mesAtual, anoAtual);
    }
}
