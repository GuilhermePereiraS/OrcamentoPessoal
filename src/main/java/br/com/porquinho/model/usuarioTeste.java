package br.com.porquinho.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class usuarioTeste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String nome;

    public String email;

}
