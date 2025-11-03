package br.com.porquinho.repository;

import br.com.porquinho.model.Extrato;
import br.com.porquinho.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExtratoRepository {
    private final JdbcTemplate template;

    public ExtratoRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void salvarEntrada(Extrato extrato) {
        try {
            String sql = "INSERT INTO extrato (descricao, tp_transacao, vl_transacao, dt_transacao, id_usuario) VALUES (?, ?, ?, ?, ?)";
            template.update(sql, extrato.getDescricao(), extrato.getTp_transacao(), extrato.getVl_transacao(), extrato.getDt_transacao(), extrato.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvarSaida(Extrato extrato) {
        try {
            String sql = "INSERT INTO extrato (descricao, tp_transacao, vl_transacao, dt_transacao, id_usuario, id_forma_pgmt, id_tipo_gasto) VALUES (?, ?, ?, ?, ?, ?, ?)";
            template.update(sql, extrato.getDescricao(), extrato.getTp_transacao(), extrato.getVl_transacao(), extrato.getDt_transacao(), extrato.getId_usuario(), extrato.getId_forma_pgmt(), extrato.getId_tipo_gasto());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
