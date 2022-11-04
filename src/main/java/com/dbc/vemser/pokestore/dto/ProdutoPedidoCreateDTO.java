package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.PedidoEntity;
import com.dbc.vemser.pokestore.entity.ProdutoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProdutoPedidoCreateDTO {

    @NotNull
    @Schema(description = "Id do produto")
    private Integer idProduto;

    @NotNull
    @Schema(description = "Produto no pedido")
    private ProdutoDTO produto;

    @NotNull
    @Schema(description = "Pedido do usuário")
    private PedidoDTO pedido;

}
