package br.com.porquinho.Repository;

import br.com.porquinho.model.Usuario;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Usuario findByLoginESenha(String login, String senha) {
        Usuario usuario = new Usuario();

        try {
            String hql = "from Usuario u where u.login = :login and u.senha = :senha";
            Query query = em.createQuery(hql);
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            usuario = (Usuario) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return usuario;
    }
}
