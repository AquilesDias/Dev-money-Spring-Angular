package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import com.github.aquiles.devmoneyapi.service.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/lancamentos")
public class LancamentoResource {

    private final LancamentoRepository repository;
    @Autowired
    private LancamentoService service;


    //    ****    METHOD GET    ****
    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable page){
        return repository.filtrar(filter, page);
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

    //    ****    METHOD DELETE    ****

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{cod}")
    public void deleteLancamento(@PathVariable Long cod){
        service.deleteLancamento(cod);
    }
}
