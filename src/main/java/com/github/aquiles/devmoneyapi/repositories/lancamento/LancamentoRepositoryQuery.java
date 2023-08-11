package com.github.aquiles.devmoneyapi.repositories.lancamento;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoRepositoryQuery  {

    Page <Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
