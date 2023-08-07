package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.model.Pessoa;
import com.github.aquiles.devmoneyapi.repositories.PessoaRepository;
import com.github.aquiles.devmoneyapi.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pessoas")
public class PessoaResource {

    private final PessoaRepository repository;

    private final PessoaService service;

    //    ****    METHOD GET    ****
    @GetMapping
    public List<Pessoa> findAll(){
        return service.findAll();
    }

    @GetMapping("{cod}")
    public Pessoa findById(@PathVariable Long cod){
        return service.findById(cod);
    }

    //    ****    METHOD POST    ****
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pessoa save(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response){
        return service.save(pessoa, response);
    }

    //    ****    METHOD PUT    ****
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{cod}")
    public void updatePessoa(@RequestBody Pessoa pessoa, @PathVariable Long cod){
        service.update(pessoa, cod);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{cod}/ativar")
    public void updatePropriedadePessoa(@RequestBody Boolean ativo, @PathVariable Long cod){
        service.updatePropriedadeAtivoPessoa(ativo, cod);
    }

    //    ****    METHOD DELETE    ****
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{cod}")
    public void delete(@PathVariable Long cod){
        service.deletePessoa(cod);
    }

}
