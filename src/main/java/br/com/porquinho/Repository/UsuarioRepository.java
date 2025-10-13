package br.com.porquinho.Repository;

import br.com.porquinho.model.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate template;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    public Usuario findByLoginESenha(String login, String senha) {
        Usuario usuario = new Usuario();
        try {
            String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
            return template.queryForObject(sql, new Object[]{login,senha}, new BeanPropertyRowMapper<>(Usuario.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuario (login, senha, nome, dt_nascimento) VALUES (?,?,?,?)";
            template.update(sql, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getDt_nascimento());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
