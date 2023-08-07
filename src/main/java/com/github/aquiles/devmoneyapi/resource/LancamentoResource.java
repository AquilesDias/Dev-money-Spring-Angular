package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import com.github.aquiles.devmoneyapi.service.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private LancamentoService service;


    //    ****    METHOD GET    ****
    @GetMapping
    public List<Lancamento> findAll(){
        return service.findAll();
    }

    @GetMapping("{cod}")
    public Lancamento findById(@PathVariable Long cod) {
        return service.findById(cod);
    }

    //    ****    METHOD POST    ****
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Lancamento save(@RequestBody Lancamento lancamento, HttpServletResponse response){
        return service.save(lancamento, response);
    }

}
