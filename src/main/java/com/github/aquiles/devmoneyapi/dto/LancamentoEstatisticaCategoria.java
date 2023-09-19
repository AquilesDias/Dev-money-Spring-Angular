package com.github.aquiles.devmoneyapi.dto;

import com.github.aquiles.devmoneyapi.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LancamentoEstatisticaCategoria {

    private Categoria categoria;
    private BigDecimal bigDecimal;
}
