package com.dbc.vemser.pokestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProdutoPedidoCreateDTO {

    @NotNull
    private Integer quantidade;

    @NotNull
    @Schema(description = "Produto no pedido")
    private ProdutoDTO produto;
}
