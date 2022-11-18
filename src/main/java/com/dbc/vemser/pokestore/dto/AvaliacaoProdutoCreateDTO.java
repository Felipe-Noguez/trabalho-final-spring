package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AvaliacaoProdutoCreateDTO {

    @NotEmpty
    private String avaliacaoProduto;
    @NotNull
    private Double nota;
}
