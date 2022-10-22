package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UsuarioDTO extends UsuarioCreateDTO{

    @NotNull
    @Positive
    private Integer idUsuario;
}
