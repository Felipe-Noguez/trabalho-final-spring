package com.dbc.vemser.pokestore.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class Cupom {


    @NotNull
    @Positive
    private Integer idCupom;

    @NotNull
    @Positive
    private double valor;

    @NotNull
    private String deletado;


    public Cupom(){
        this.setDeletado("F");
    }

    public Cupom(Integer idCupom, double valor) {
        this.idCupom = idCupom;
        this.valor = valor;
        this.setDeletado("F");
    }
}
