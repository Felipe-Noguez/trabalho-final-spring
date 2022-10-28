package com.dbc.vemser.pokestore.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cupom {


    private Integer idCupom;

    private Double valor;

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
