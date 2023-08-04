package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/lancamentos")
public class LancamentoResource {

    private final LancamentoRepository repository;


    //    ****    METHOD GET    ****
    @GetMapping
    public List<Lancamento> findAll(){
        return repository.findAll();
    }

    @GetMapping("{cod}")
    public Lancamento findById(@PathVariable Long cod) {

        return repository
                .findById(cod)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento não encontrado."));
    }

    //    ****    METHOD POST    ****
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Lancamento save(@RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento newLancamento = repository.save(lancamento);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(newLancamento.getCod()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return newLancamento;
    }

}
