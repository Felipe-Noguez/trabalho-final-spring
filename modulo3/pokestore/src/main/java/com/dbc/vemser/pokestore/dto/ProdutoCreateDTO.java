package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.Usuario;
import com.dbc.vemser.pokestore.enums.Tipos;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ProdutoCreateDTO {


    @NotNull
    @Size(max = 250)
    private String nome;

    @NotNull
    @Size(max = 250)
    private String descricao;

    @NotNull
    @Positive
    private int quantidade;

    @NotNull
    protected Tipos tipo;

    @NotNull
    private double valor;

    private int idUsuario;

    @NotBlank
    private String deletado;

}
