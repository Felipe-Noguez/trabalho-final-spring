package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PedidoDTO extends PedidoCreateDTO {

    @NotNull
    @Positive
    private int idPedido;

    @NotNull
    @Positive
    private int idUsuario;
}
