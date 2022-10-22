package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.Usuario;
import com.dbc.vemser.pokestore.enums.Tipos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProdutoCreateDTO {

    @NotBlank
    @Size(max = 250)
    private String nome;

    @NotBlank
    @Size(max = 250)
    private String descricao;

    @NotBlank
    @Positive
    private int quantidade;

    @NotBlank
    @Positive
    private Tipos tipo;

    @NotBlank
    @Positive
    private double valor;

    @NotBlank
    @Positive
    private int idUsuario;

    @NotBlank
    private String deletado;

    Usuario usuario;
}
