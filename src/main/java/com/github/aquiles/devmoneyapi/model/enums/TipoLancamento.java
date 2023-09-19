package com.github.aquiles.devmoneyapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoLancamento {

    RECEITA("Receita"),
    DESPESA("Despesa") ;

    private String descricao;

}
