package com.github.aquiles.devmoneyapi.repositories.lancamento;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery  {

    List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
