package com.github.aquiles.devmoneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cod;

    @NotEmpty(message = "{field.name.invalid}")
    @Size(min = 3, max = 50, message = "field NAME: Size min 3, max 50.")
    private String name;
}
