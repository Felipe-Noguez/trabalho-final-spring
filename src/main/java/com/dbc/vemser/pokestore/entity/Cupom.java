package com.dbc.vemser.pokestore.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cupom {

    private Integer idCupom;
    private Double valor;
    private String deletado = "F";
}
