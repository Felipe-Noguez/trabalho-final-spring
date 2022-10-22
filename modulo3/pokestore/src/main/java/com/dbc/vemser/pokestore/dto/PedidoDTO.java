package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PedidoDTO extends PedidoCreateDTO {

    @NotNull
    @Positive
    private Integer idPedido;

    @NotNull
    @Positive
    private Integer idUsuario;
}
