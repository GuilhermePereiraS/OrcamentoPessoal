package br.com.porquinho.service;

import br.com.porquinho.model.FormaPagamento;
import br.com.porquinho.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listarTodos(){
        return formaPagamentoRepository.listarTodos();
    }
}
