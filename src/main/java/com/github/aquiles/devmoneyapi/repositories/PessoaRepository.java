package com.github.aquiles.devmoneyapi.repositories;

import com.github.aquiles.devmoneyapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
