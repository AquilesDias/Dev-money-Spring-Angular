package com.github.aquiles.devmoneyapi.dto;

import com.github.aquiles.devmoneyapi.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class LancamentoEstatisticaDia {

    private TipoLancamento tipoLancamento;
    private LocalDate localDate;
    private BigDecimal bigDecimal;
}
