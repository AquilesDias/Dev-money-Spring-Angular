package com.github.aquiles.devmoneyapi.repositories.lancamento;

import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaCategoria;
import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaDia;
import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import com.github.aquiles.devmoneyapi.repositories.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepositoryQuery  {

    Page <Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    Page<ResumoLancamento> resumo(LancamentoFilter lancamentoFilter, Pageable pageable);

    List<LancamentoEstatisticaCategoria> porCategoria(LocalDate localDate);
    List<LancamentoEstatisticaDia> porDia(LocalDate localDate);
}
