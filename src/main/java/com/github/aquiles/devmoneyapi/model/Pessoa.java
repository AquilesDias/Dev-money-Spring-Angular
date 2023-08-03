package com.github.aquiles.devmoneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cod;

    @NotEmpty(message = "{field.name.invalid}")
    private String nome;

    @NotNull(message = "{field.ativo.invalid}")
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

}
