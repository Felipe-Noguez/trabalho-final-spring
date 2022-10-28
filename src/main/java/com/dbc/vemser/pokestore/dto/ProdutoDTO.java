package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProdutoDTO extends ProdutoCreateDTO {

    @NotNull
    private Integer idProduto;
}
