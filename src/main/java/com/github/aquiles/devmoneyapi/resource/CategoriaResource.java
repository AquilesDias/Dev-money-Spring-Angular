package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Categoria;
import com.github.aquiles.devmoneyapi.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/categorias")
public class CategoriaResource {

    @Autowired
    private final CategoriaRepository categoria;

    @GetMapping
    public List<Categoria> findAll(){
        return categoria.findAll();
    }

}
