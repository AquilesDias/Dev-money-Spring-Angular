package com.github.aquiles.devmoneyapi.repositories.lancamento.impl;

import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaCategoria;
import com.github.aquiles.devmoneyapi.dto.LancamentoEstatisticaDia;
import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.model.Lancamento_;
import com.github.aquiles.devmoneyapi.model.Pessoa_;
import com.github.aquiles.devmoneyapi.model.Categoria_;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import com.github.aquiles.devmoneyapi.repositories.lancamento.LancamentoRepositoryQuery;
import com.github.aquiles.devmoneyapi.repositories.projection.ResumoLancamento;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> lancamentoCriteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = lancamentoCriteriaQuery.from(Lancamento.class);

        // criar as restrições
        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        lancamentoCriteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = entityManager.createQuery(lancamentoCriteriaQuery);
        addRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(),pageable, total(lancamentoFilter));
    }

    @Override
    public Page<ResumoLancamento> resumo(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(
                builder.construct(ResumoLancamento.class,
                    root.get(Lancamento_.cod),
                    root.get(Lancamento_.descricao),
                    root.get(Lancamento_.dataVencimento),
                    root.get(Lancamento_.dataPagamento),
                    root.get(Lancamento_.valor),
                    root.get(Lancamento_.tipoLancamento),
                    root.get(Lancamento_.pessoa).get(Pessoa_.nome),
                    root.get(Lancamento_.categoria).get(Categoria_.name)
                ));

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteria);
        addRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }


    private void addRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
            int paginaAtual = pageable.getPageNumber();
            int registrosPorPagina = pageable.getPageSize();
            int primeiroRegistroDaPag = paginaAtual * registrosPorPagina;

            query.setFirstResult(primeiroRegistroDaPag);
            query.setMaxResults(registrosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> lancamentoCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Lancamento> root = lancamentoCriteriaQuery.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        lancamentoCriteriaQuery.where(predicates);

        lancamentoCriteriaQuery.select(criteriaBuilder.count(root));

        return entityManager.createQuery(lancamentoCriteriaQuery).getSingleResult();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder, Root<Lancamento> root){

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())){
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("descricao")),
                            "%" + lancamentoFilter.getDescricao().toLowerCase()+ "%"));
        }

        if(lancamentoFilter.getDataVencimentoDe() != null){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dataVencimento"),
                        lancamentoFilter.getDataVencimentoDe()));
        }

        if(lancamentoFilter.getDataVencimentoAte() != null){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            root.get("dataPagamento"),
                            lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);

    }

    @Override
    public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<LancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder
                .createQuery(LancamentoEstatisticaCategoria.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class,
                root.get(Lancamento_.categoria),
                criteriaBuilder.sum(root.get(Lancamento_.valor))));

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
                criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia));

        criteriaQuery.groupBy(root.get(Lancamento_.categoria));

        TypedQuery<LancamentoEstatisticaCategoria> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }


    @Override
    public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<LancamentoEstatisticaDia> criteriaQuery = criteriaBuilder
                .createQuery(LancamentoEstatisticaDia.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(criteriaBuilder.construct( LancamentoEstatisticaDia.class,
                root.get(Lancamento_.tipoLancamento),
                root.get(Lancamento_.dataVencimento),
                criteriaBuilder.sum(root.get(Lancamento_.valor)) ));

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia   = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
                criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia));

        criteriaQuery.groupBy(root.get(Lancamento_.tipoLancamento),
                root.get(Lancamento_.dataVencimento) );


        TypedQuery<LancamentoEstatisticaDia> typedQuery = entityManager.createQuery(criteriaQuery);
        log.info("LancamentoRepositoryImpl RECECE {}", typedQuery.getResultList());
        return typedQuery.getResultList();
    }
}
