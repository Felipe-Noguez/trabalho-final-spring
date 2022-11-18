package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PagamentoDTO extends PagamentoCreateDTO {
    private String idPagamento;
    private LocalDate dataPagamento;
    private Double valorTotal;
}
