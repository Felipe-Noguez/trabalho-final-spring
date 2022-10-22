package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CupomCreateDTO {

    @NotNull
    @Positive
    private double valor;

    @NotNull
    private String deletado;
}
