package com.dbc.vemser.pokestore.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class ProdutoPedido {

    private Integer idProdutoPedido;


    private Produto produto;


    private Pedido pedido;


    private Integer quantidade;


    private Double valor;


    private String deletado;

    public ProdutoPedido(){
        this.setDeletado("F");
    }

    public ProdutoPedido(Integer idProdutoPedido, Produto produto, Pedido pedido, Integer quantidade, Double valor) {
        this.idProdutoPedido = idProdutoPedido;
        this.produto = produto;
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.valor = valor;
        this.setDeletado("F");
    }
}
