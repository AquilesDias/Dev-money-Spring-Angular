package com.github.aquiles.devmoneyapi.service;

import com.github.aquiles.devmoneyapi.exception.PessoaInexistenteOuInativaException;
import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.model.Pessoa;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import com.github.aquiles.devmoneyapi.repositories.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Service
@Slf4j
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    PessoaRepository repositoryPessoa;

    public Lancamento findById(Long cod){
      return repository.findById(cod).orElseThrow(
               () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lançamento não encontrado."));
    }

    public List<Lancamento> findAll(){
        return repository.findAll();
    }

    public Lancamento save(Lancamento lancamento, HttpServletResponse response){

        Pessoa pessoa = repositoryPessoa
                .findById(lancamento.getPessoa().getCod())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada!"));

        if(pessoa == null || pessoa.isAtivo() ){
            throw new PessoaInexistenteOuInativaException("Pessoa inexistente ou inativa!");
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{cod}")
                .buildAndExpand(lancamento.getCod()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return repository.save(lancamento);
    }
}
