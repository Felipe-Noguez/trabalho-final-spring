package com.dbc.vemser.pokestore.dto;

import com.dbc.vemser.pokestore.entity.Usuario;
import com.dbc.vemser.pokestore.enums.Tipos;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ProdutoCreateDTO {

    //    @NotBlank
//    @Positive
    private Integer idProduto;

    //    @NotNull
//    @Size(max = 250)
    private String nome;

    //    @NotNull
//    @Size(max = 250)
    private String descricao;

    //    @NotBlank
//    @Positive
    private int quantidade;

    //    @NotNull
    protected Tipos tipo;

    //    @NotBlank
//    @Positive
    private double valor;

    private int idUsuario;

    //    @NotBlank
    private String deletado;

}
