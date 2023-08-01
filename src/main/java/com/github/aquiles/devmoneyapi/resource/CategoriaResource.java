package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Categoria;
import com.github.aquiles.devmoneyapi.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/categorias")
public class CategoriaResource {

    @Autowired
    private final CategoriaRepository repository;

    // ****    GET MAPPING    *****
    @GetMapping
    public List<Categoria> findAll(){
        return repository.findAll();
    }

    @GetMapping("{cod}")
    public Categoria findById(@PathVariable Long cod){

        log.info("Obtaing cod: {}", cod);

            return repository
                .findById(cod)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria not found!"));
    }

    // ****    POST MAPPING    *****
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria save(@RequestBody Categoria categoria, HttpServletResponse response){
        Categoria newCategoria = repository.save(categoria);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(newCategoria.getCod()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return newCategoria;
    }

}
