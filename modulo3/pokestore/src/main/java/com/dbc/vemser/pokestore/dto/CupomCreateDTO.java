package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CupomCreateDTO {

    @NotNull
    @Positive
    private double valor;

    @NotNull
    private String deletado;
}
