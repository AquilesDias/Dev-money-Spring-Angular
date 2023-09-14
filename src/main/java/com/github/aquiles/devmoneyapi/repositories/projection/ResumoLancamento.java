package com.github.aquiles.devmoneyapi.repositories.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.aquiles.devmoneyapi.model.enums.TipoLancamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ResumoLancamento {

    private Long cod;
    private String descricao;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;
    private BigDecimal valor;
    private TipoLancamento tipoLancamento;
    private String pessoa;
    private String categoria;

    public ResumoLancamento(Long cod, String descricao, Date dataVencimento, Date dataPagamento,
                            BigDecimal valor, TipoLancamento tipoLancamento, String pessoa, String categoria) {
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
