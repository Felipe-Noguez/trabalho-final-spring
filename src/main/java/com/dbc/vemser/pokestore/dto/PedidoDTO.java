package com.dbc.vemser.pokestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoDTO {

    private Double valorFinal;
    private Integer idPedido;
    private List<ProdutoPedidoDTO> produtosPedido = new ArrayList<>();
}
