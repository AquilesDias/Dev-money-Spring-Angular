package com.github.aquiles.devmoneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cod;
    private String name;
}
