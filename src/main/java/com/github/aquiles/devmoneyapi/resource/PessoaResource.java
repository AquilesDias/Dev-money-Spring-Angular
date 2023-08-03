package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Pessoa;
import com.github.aquiles.devmoneyapi.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pessoas")
public class PessoaResource {

    private final PessoaRepository repository;

    //    ****    GET POST    ****
    @GetMapping
    public List<Pessoa> findAll(){
        return repository.findAll();
    }

    @GetMapping("{cod}")
    public Pessoa findById(@PathVariable Long cod){
        Pessoa pessoa = repository
                .findById(cod)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontra."));
        return pessoa;
    }


    //    ****    GET POST    ****
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pessoa save(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response){
        Pessoa newPessoa = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(newPessoa.getCod()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return newPessoa;
    }
}
