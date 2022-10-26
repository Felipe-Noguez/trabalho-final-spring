package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class UsuarioDTO extends UsuarioCreateDTO{

    @NotNull
    private Integer idUsuario;
}
