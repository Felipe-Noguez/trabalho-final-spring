package com.dbc.vemser.pokestore.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    private String deletado;

    public Pedido() {
        this.setDeletado("F");
    }

    public Pedido(List<ProdutoPedido> produtosPedido, Cupom cupom, Integer idPedido, Integer idUsuario, double valorFinal) {
        this.produtosPedido = produtosPedido;
        this.cupom = cupom;
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.valorFinal = valorFinal;
        this.setDeletado("F");
    }
}
