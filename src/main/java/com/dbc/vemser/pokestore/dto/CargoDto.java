package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoDto {
    @NotNull
    private String nome;
}
