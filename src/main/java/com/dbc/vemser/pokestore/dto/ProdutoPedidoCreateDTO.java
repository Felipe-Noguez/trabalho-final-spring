package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.Pedido;
import com.dbc.vemser.pokestore.entity.Produto;
import lombok.Data;

@Data
public class ProdutoPedidoCreateDTO {

    private Integer idProduto;

    private Produto produto;

    private Pedido pedido;

}
