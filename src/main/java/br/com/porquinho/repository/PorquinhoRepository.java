package br.com.porquinho.repository;

import br.com.porquinho.model.Porquinho;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PorquinhoRepository {

    private final JdbcTemplate template;

    public PorquinhoRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }


    public void salvar(Porquinho porquinho) {
        try {
            String sql = "INSERT INTO porquinho (id_usuario, nome_meta, vl_alcancado, vl_necessario, dt_meta) VALUES (?,?,?,?,?)";
            template.update(sql, porquinho.getId_usuario(), porquinho.getNome_meta(), porquinho.getVl_alcancado(), porquinho.getVl_necessario(), porquinho.getDt_meta());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Porquinho> listarTodos(Integer idUsuario) {
        try {
            String sql = "SELECT id_porquinho, nome_meta, vl_alcancado, vl_necessario, dt_meta FROM porquinho pq WHERE id_usuario = ? ORDER BY pq.dt_meta ASC";
            return (List<Porquinho>) template.query(sql, new BeanPropertyRowMapper<>(Porquinho.class), idUsuario);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void atualizar(Porquinho porquinho) {
        try {
            String sql = "UPDATE porquinho SET nome_meta = ?, vl_alcancado = ?, vl_necessario = ?, dt_meta = ? WHERE id_porquinho = ?";
            template.update(sql, porquinho.getNome_meta(), porquinho.getVl_alcancado(), porquinho.getVl_necessario(), porquinho.getDt_meta(), porquinho.getId_porquinho());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Porquinho porquinho) {
        try {
            String sql = "DELETE FROM porquinho WHERE id_porquinho = ?";
            template.update(sql, porquinho.getId_porquinho());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
