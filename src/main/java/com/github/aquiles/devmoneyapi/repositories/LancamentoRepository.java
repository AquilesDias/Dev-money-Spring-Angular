package com.github.aquiles.devmoneyapi.repositories;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
