package com.dbc.vemser.pokestore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TopicoCupomDto extends CupomCreateDTO{

    @NotNull
    private String email;
}
