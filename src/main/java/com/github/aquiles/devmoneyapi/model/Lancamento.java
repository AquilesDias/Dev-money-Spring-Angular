package com.github.aquiles.devmoneyapi.model;

import com.github.aquiles.devmoneyapi.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @Column(name = "data_pagamento")
    private Date dataPagamento;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

}
