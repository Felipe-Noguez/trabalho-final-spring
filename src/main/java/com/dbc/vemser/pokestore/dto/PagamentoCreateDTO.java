package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.enums.FormaPagamento;
import com.dbc.vemser.pokestore.enums.StatusPagamento;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PagamentoCreateDTO {

    @NotNull
    private Integer pedidoId;
    @NotNull
    private StatusPagamento status;
    @NotNull
    private FormaPagamento formaPagamento;
}
