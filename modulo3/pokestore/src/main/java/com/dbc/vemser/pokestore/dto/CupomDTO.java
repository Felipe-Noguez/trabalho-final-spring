package com.dbc.vemser.pokestore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CupomDTO extends CupomCreateDTO{

    @NotNull
    @Positive
    private Integer idCupom;

}
