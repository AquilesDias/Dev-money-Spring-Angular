package com.github.aquiles.devmoneyapi.service;

import com.github.aquiles.devmoneyapi.model.Pessoa;
import com.github.aquiles.devmoneyapi.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;

    public List<Pessoa> findAll(){
        return repository.findAll();
    }

    public Pessoa findById(Long cod){
        Pessoa pessoa = repository
                .findById(cod)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
        return pessoa;
    }

    public Pessoa save(Pessoa pessoa, HttpServletResponse response){
        Pessoa newPessoa = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(newPessoa.getCod()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return newPessoa;
    }

    public void update(Pessoa pessoa, Long cod){
        repository
                .findById(pessoa.getCod())
                .map(p -> {
                    pessoa.setCod(p.getCod());
                    return repository.save(pessoa);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrado"));
    }

    public void updatePropriedadeAtivoPessoa(Boolean ativo, Long cod){

        Pessoa updateAtivo = findById(cod);
        updateAtivo.setAtivo(ativo);
        repository.save(updateAtivo);
    }

    public void deletePessoa(Long cod){
        Pessoa pessoa = findById(cod);
        repository.delete(pessoa);
    }
}
