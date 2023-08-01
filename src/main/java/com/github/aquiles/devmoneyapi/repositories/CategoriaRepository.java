package com.github.aquiles.devmoneyapi.repositories;

import com.github.aquiles.devmoneyapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
