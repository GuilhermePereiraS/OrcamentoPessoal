package br.com.porquinho.service;

import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.com.porquinho.util.PorquinhoUtils;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {
        if (PorquinhoUtils.estaVazioOuNulo(usuario.getNome())
                || PorquinhoUtils.estaVazioOuNulo(usuario.getLogin())
                || PorquinhoUtils.estaVazioOuNulo(usuario.getSenha())
                || usuario.getDt_nascimento() == null) {
            //logica pra enviar mensagem que ta faltando coisa
            return;
        }
        if (usuario.getNome().length() <= 3) {
            //Enviar mensagem que precisa de pelo menos 4 caracteres
            return;
        }
        if (!PorquinhoUtils.temCaracterEspecial(usuario.getSenha()) ) {
            //Enviar mensagem de senha sem caractere especial o
            return;
        }
        if (usuario.getSenha().length() <= 4) {
            //Enviar mensagem de senha tem menos de 5 digitos
            return;
        }

        //criptografia
        usuario.setSenha(encoder.encode(usuario.getSenha()));

        usuarioRepository.salvar(usuario);
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.excluir(usuario);
    }

    public Usuario encontraPorLoginESenha(String login, String senha) {
        if (PorquinhoUtils.estaVazioOuNulo(login) || PorquinhoUtils.estaVazioOuNulo(senha)) {
            // Enviar mensagem que ta vazio

            return null;
        }
        return usuarioRepository.encontraPorLoginESenha(login, senha);
    }

    public boolean validaSenhaUsuarioUsuario(String senhaInserida, String hash) {
        return encoder.matches(senhaInserida, hash);
    }

    public Usuario encontraPorLoginEHashSenha(String login, String senhaForm) {
        Usuario usuarioNoBanco = usuarioRepository.encontraPorLogin(login);

        if (usuarioNoBanco == null) {
            return null;
        }
        if (validaSenhaUsuarioUsuario(senhaForm, usuarioNoBanco.getSenha())) {
            return usuarioNoBanco;
        }
        else {
            //msg
            return null;
        }
    }

    public Usuario encontraPorLogin(String login) {
        Usuario usuario = usuarioRepository.encontraPorLogin(login);

        return usuario;
    }

}
