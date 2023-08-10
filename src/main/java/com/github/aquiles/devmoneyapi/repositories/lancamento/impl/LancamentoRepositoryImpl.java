package com.github.aquiles.devmoneyapi.repositories.lancamento.impl;

import com.github.aquiles.devmoneyapi.model.Lancamento;
import com.github.aquiles.devmoneyapi.repositories.filter.LancamentoFilter;
import com.github.aquiles.devmoneyapi.repositories.lancamento.LancamentoRepositoryQuery;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> lancamentoCriteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
        Root<Lancamento> root = lancamentoCriteriaQuery.from(Lancamento.class);

        // criar as restrições
        Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
        lancamentoCriteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = entityManager.createQuery(lancamentoCriteriaQuery);
        return query.getResultList();
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
}
