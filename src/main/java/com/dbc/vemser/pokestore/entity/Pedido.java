package com.dbc.vemser.pokestore.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Pedido {

    private List<ProdutoPedido> produtosPedido = new ArrayList<>(); // ID
    private Cupom cupom; // ID

    private Integer idCupom;
    private Integer idPedido;
    private Integer idUsuario;
    private double valorFinal;
    private String deletado = "F";

}
