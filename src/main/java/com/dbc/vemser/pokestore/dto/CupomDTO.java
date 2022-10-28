package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CupomDTO extends CupomCreateDTO{

    private Integer idCupom;

}
