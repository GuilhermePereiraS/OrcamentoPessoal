package br.com.porquinho.service;

import br.com.porquinho.model.Porquinho;
import br.com.porquinho.repository.PorquinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PorquinhoService {

    @Autowired
    PorquinhoRepository porquinhoRepository;

    public void salvar(Porquinho porquinho) {
        porquinhoRepository.salvar(porquinho);
    }

    public void atualizar(Porquinho porquinho) {
        porquinhoRepository.atualizar(porquinho);
    }

    public void excluir(Porquinho porquinho) {
        porquinhoRepository.excluir(porquinho);
    }

    public List<Porquinho> listarTodos(Integer idUsuario) {
        return porquinhoRepository.listarTodos(idUsuario);
    }

}
