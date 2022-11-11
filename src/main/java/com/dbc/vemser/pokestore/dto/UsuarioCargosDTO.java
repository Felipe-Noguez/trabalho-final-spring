package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UsuarioCargosDTO {

    @NotNull
    private Integer idUsuario;
    @NotNull
    private List<CargoDto> cargos;
}
