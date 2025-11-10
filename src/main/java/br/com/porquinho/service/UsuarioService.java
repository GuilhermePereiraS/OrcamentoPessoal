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
    private final OrcamentoService orcamentoService;
    private final OrcamentoTipoGastoService orcamentoTipoGastoService;

    public UsuarioService(UsuarioRepository usuarioRepository, OrcamentoService orcamentoService, OrcamentoTipoGastoService orcamentoTipoGastoService) {
        this.usuarioRepository = usuarioRepository;
        this.orcamentoService = orcamentoService;
        this.orcamentoTipoGastoService = orcamentoTipoGastoService;
    }

    public void salvar(Usuario usuario) {
        if (temUsuarioComLogin(usuario.getLogin())) {
            throw new LoginJaExistenteException("Este login já está em uso.");
        }

        usuario.setSenha(encoder.encode(usuario.getSenha()));

        usuarioRepository.salvar(usuario);
        Usuario usuarioNoBanco = encontraPorLogin(usuario.getLogin());

        if (usuarioNoBanco != null) {
            var idUsuario = usuarioNoBanco.getId_usuario();
            orcamentoService.criaOrcamentoDoMesPadrao(idUsuario);

            var idOrcamentoAtual = orcamentoService.pegaOrcamentoAtual(idUsuario).getId_orcamento();
            orcamentoTipoGastoService.criaOrcamentoTipoGastoPadrao(idOrcamentoAtual);
        }
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
        return usuarioRepository.encontraPorLogin(login);
    }

    public Boolean temUsuarioComLogin(String login) {
        return usuarioRepository.temUsuarioComLogin(login);
    }

}
