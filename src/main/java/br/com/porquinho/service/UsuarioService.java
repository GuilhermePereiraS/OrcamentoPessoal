package br.com.porquinho.service;

import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {

        //criptografia

        usuario.setSenha(encoder.encode(usuario.getSenha()));

        usuarioRepository.salvar(usuario);
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.excluir(usuario);
    }

    public Usuario encontraPorLoginESenha(String login, String senha) {
        return usuarioRepository.encontraPorLoginESenha(login, senha);
    }

    public Usuario encontraPorLoginEHashSenha(String login, String senha) {
        Usuario usuario = usuarioRepository.encontraPorLogin(login);

        if (usuario == null) {
            return null;
        }
        if (encoder.matches(senha, usuario.getSenha())) {
            return usuarioRepository.encontraPorLogin(login);
        }
        else {
            //msg
            return null;
        }

    }

}
