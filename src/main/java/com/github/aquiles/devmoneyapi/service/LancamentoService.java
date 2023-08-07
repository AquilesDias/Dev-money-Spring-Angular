package com.github.aquiles.devmoneyapi.service;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public Lancamento findById(Long cod){
      return repository.findById(cod).orElseThrow(
               () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento não encontrado."));
    }

    public List<Lancamento> findAll(){
        return repository.findAll();
    }

    public Lancamento save(Lancamento lancamento, HttpServletResponse response){
        Lancamento newLancamento = repository.save(lancamento);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(newLancamento.getCod()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return newLancamento;
    }
}
