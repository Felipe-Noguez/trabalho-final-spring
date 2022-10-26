package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoDTO {

    private double valorFinal;

    private Integer idPedido;

    private List<ProdutoPedidoDTO> produtosPedido = new ArrayList<>();

}
