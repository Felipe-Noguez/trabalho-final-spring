package com.dbc.vemser.pokestore.enums;

import java.util.Arrays;

public enum Tipos {
    JOGOS("0"),
    CONSOLE("1"),
    COLECIONAVEL("2");

    private String tipo;

    Tipos(String tipo){

        this.tipo = tipo;
    }

    public String getTipos() {

        return tipo;
    }

    public static Tipos ofTipo(String tipo) {
        return Arrays.stream(Tipos.values())
                .filter(tp -> tp.getTipos().equals(tipo))
                .findFirst()
                .get();
    }
}