package com.dbc.vemser.pokestore.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Pedido {

    @NotNull
    private List<ProdutoPedido> produtosPedido = new ArrayList<>(); // ID

    @Positive
    private Cupom cupom; // ID

    @NotNull
    @Positive
    private Integer idPedido;

    @NotNull
    @Positive
    private Integer idUsuario;

    @Positive
    private double valorFinal;

    @NotBlank
    private String deletado;

    public Pedido() {
        this.setDeletado("F");

    }

    public Pedido(List<ProdutoPedido> produtosPedido, Cupom cupom, int idPedido, int idUsuario, double valorFinal) {
        this.produtosPedido = produtosPedido;
        this.cupom = cupom;
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.valorFinal = valorFinal;
        this.setDeletado("F");
    }
}
