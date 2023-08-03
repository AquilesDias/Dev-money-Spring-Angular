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

    //    ****    METHOD POST    ****
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

    //    ****    METHOD POST    ****
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

    //    ****    METHOD DELETE    ****
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{cod}")
    public void updatePessoa(@RequestBody Pessoa pessoa, @PathVariable Long cod){

        repository
                .findById(cod)
                .map(c -> {
                    pessoa.setCod(c.getCod());
                    return repository.save(pessoa);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrado"));

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{cod}/ativar")
    public void updatePropriedadePessoa(@RequestBody Boolean ativo, @PathVariable Long cod){
        Pessoa updatePropriedade = repository
                .findById(cod)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa inexistente."));

        updatePropriedade.setAtivo(ativo);
        repository.save(updatePropriedade);
    }

    //    ****    METHOD DELETE    ****
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{cod}")
    public void delete(@PathVariable Long cod){

        Pessoa pessoa = repository
                .findById(cod)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa inexistente."));

        repository.delete(pessoa);
    }

}
