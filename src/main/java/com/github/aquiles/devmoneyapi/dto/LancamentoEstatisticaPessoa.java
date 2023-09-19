package com.github.aquiles.devmoneyapi.dto;

import com.github.aquiles.devmoneyapi.model.Pessoa;
import com.github.aquiles.devmoneyapi.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class LancamentoEstatisticaPessoa {

    private Pessoa pessoa;
    private TipoLancamento tipo;
    private BigDecimal total;
}
