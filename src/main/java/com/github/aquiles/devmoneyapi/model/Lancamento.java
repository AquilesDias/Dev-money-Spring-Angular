package com.github.aquiles.devmoneyapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.aquiles.devmoneyapi.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lancamento {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cod;

    private String descricao;
    private String observacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_lancamento")
    private TipoLancamento tipoLancamento;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;


    public Lancamento(Long cod, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
                      BigDecimal valor, TipoLancamento tipoLancamento, Pessoa pessoa, Categoria categoria) {

        this.cod = cod;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.tipoLancamento = tipoLancamento;
        this.pessoa = pessoa;
        this.categoria = categoria;
    }
}
