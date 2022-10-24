package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.Cupom;
import com.dbc.vemser.pokestore.entity.ProdutoPedido;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoCreateDTO {

    @NotNull
    @Positive
    private Integer idPedido;

    @Positive
    private double valorFinal;

    @NotBlank
    private String deletado;

    @Positive
    private Cupom cupom; // ID

    @NotNull
    private List<ProdutoPedido> produtosPedido = new ArrayList<>(); // ID
}
