package com.github.aquiles.devmoneyapi.resource;

import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaCategoria;
import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaDia;
import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.LancamentoRepository;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import com.github.aquiles.devmoneyapi.repositories.projection.ResumoLancamento;
import com.github.aquiles.devmoneyapi.service.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("estatisticas/por-categoria")
    public List<LancamentoEstatisticaCategoria> porCategoria(){
        return repository.porCategoria(LocalDate.now());
    }

    @GetMapping("estatisticas/por-dia")
    public List<LancamentoEstatisticaDia> porDia(){
        return repository.porDia(LocalDate.now().withDayOfMonth(1));
    }

    @GetMapping(params = "resumo")
    public Page<ResumoLancamento> lancamentoResumo(LancamentoFilter filter, Pageable pageable){
        return repository.resumo(filter, pageable);
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
