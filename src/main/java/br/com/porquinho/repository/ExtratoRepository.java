package br.com.porquinho.repository;

import br.com.porquinho.model.Extrato;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Repository
public class ExtratoRepository {
    private final JdbcTemplate template;

    public ExtratoRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Integer salvarEntrada(Extrato extrato) {
        try {
            String sql = "INSERT INTO extrato (descricao, tp_transacao, vl_transacao, dt_transacao, id_usuario) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, extrato.getDescricao());
                ps.setString(2, extrato.getTp_transacao());
                ps.setBigDecimal(3, extrato.getVl_transacao());
                ps.setTimestamp(4, Timestamp.valueOf(extrato.getDt_transacao().atStartOfDay()));
                ps.setInt(5, extrato.getId_usuario());
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeyList().get(0).get("id_extrato");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer salvarSaida(Extrato extrato) {
        try {
            String sql = "INSERT INTO extrato (descricao, tp_transacao, vl_transacao, dt_transacao, id_usuario, id_forma_pgmt, id_tipo_gasto) VALUES (?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, extrato.getDescricao());
                ps.setString(2, extrato.getTp_transacao());
                ps.setBigDecimal(3, extrato.getVl_transacao());
                ps.setTimestamp(4, Timestamp.valueOf(extrato.getDt_transacao().atStartOfDay())); // LocalDate -> Timestamp
                ps.setInt(5, extrato.getId_usuario());
                ps.setInt(6, extrato.getId_forma_pgmt());
                ps.setInt(7, extrato.getId_tipo_gasto());
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeyList().get(0).get("id_extrato");
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public List<Extrato> listarTodos(Integer idUsuario) {
        try {
            String sql = "SELECT * FROM extrato WHERE id_usuario = ? ORDER BY extrato.dt_transacao DESC, extrato.id_extrato DESC";
            return template.query(sql, new BeanPropertyRowMapper<>(Extrato.class), idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BigDecimal pegarGastoDoMes(Integer idUsuario, int mes, int ano) {
        String sql = "SELECT SUM(ext.vl_transacao) FROM extrato ext " +
                "WHERE id_usuario = ? " +
                "AND ext.tp_transacao = 'saida' " +
                "AND EXTRACT(MONTH FROM dt_transacao) = ? " +
                "AND EXTRACT(YEAR FROM dt_transacao) = ?";
                ;
        return template.queryForObject(sql, BigDecimal.class, idUsuario, mes, ano);
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
