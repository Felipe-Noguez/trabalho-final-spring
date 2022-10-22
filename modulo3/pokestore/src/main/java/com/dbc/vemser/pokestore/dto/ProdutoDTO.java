package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ProdutoDTO extends ProdutoCreateDTO {

    @NotBlank
    @Positive
    private Integer idProduto;
}
