package com.dbc.vemser.pokestore.entity;

import com.dbc.vemser.pokestore.enums.Tipos;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Produto {

    private Integer idProduto;
    private String nome;
    private String descricao;
    private int quantidade;
    protected Tipos tipo;
    private double valor;
    private int idUsuario;
    private String deletado = "F";

}
