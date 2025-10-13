package br.com.porquinho.Repository;

import br.com.porquinho.model.Porquinho;
import br.com.porquinho.model.Usuario;
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


    public void save(Porquinho porquinho) {
        try {
            String sql = "INSERT INTO porquinho (id_usuario, nome_meta, dinheiro_alcancado, dinheiro_necessario, dt_meta) VALUES (?,?,?,?,?)";
            template.update(sql, porquinho.getId_usuario(), porquinho.getNome_meta(), porquinho.getDinheiro_alcancado(), porquinho.getDinheiro_necessario(), porquinho.getDt_meta());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Porquinho> findAll(Integer idUsuario) {
        try {
            String sql = "SELECT id_porquinho, nome_meta, dinheiro_alcancado, dinheiro_necessario, dt_meta FROM porquinho WHERE id_usuario = ?";
            return (List<Porquinho>) template.query(sql, new BeanPropertyRowMapper<>(Porquinho.class), idUsuario);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Porquinho porquinho) {
        try {
            String sql = "UPDATE porquinho SET nome_meta = ?, dinheiro_alcancado = ?, dinheiro_necessario = ?, dt_meta = ? WHERE id_porquinho = ?";
            template.update(sql, porquinho.getNome_meta(), porquinho.getDinheiro_alcancado(), porquinho.getDinheiro_necessario(), porquinho.getDt_meta(), porquinho.getId_porquinho());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
