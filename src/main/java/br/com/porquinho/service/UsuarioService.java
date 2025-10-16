package br.com.porquinho.service;

import br.com.porquinho.model.Usuario;
import br.com.porquinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.salvar(usuario);
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.excluir(usuario);
    }

    public Usuario encontraPorLoginESenha(String login, String senha) {
        return usuarioRepository.encontraPorLoginESenha(login, senha);
    }

}
