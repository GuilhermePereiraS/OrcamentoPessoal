package br.com.porquinho.repository;

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

    public Usuario encontraPorLoginESenha(String login, String senha) {
        try {
            String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
            return template.queryForObject(sql, new Object[]{login,senha}, new BeanPropertyRowMapper<>(Usuario.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario encontraPorLogin(String login) {
        try {
            String sql = "SELECT * FROM usuario WHERE login = ?";
            return template.queryForObject(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Usuario.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void salvar(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuario (login, senha, nome, dt_nascimento) VALUES (?,?,?,?)";
            template.update(sql, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getDt_nascimento());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Usuario usuario) {
        try {
            String sql = "DELETE FROM porquinho WHERE id_usuario = ?";
            template.update(sql, usuario.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario encontraUsuarioPorLogin(String login) {
        String sql = "SELECT * FROM usuario WHERE login = ? ";
        return template.queryForObject(sql, new Object[]{login}, new BeanPropertyRowMapper<>(Usuario.class));
    }
}
