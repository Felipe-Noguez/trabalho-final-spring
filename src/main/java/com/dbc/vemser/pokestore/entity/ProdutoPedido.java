package com.dbc.vemser.pokestore.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProdutoPedido {

    private Integer idProdutoPedido;
    private Produto produto;
    private Pedido pedido;
    private Integer quantidade;
    private Double valor;
    private String deletado = "F";

}
