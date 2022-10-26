package com.dbc.vemser.pokestore.entity;

import com.dbc.vemser.pokestore.enums.Tipos;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

    private String deletado;


    public Produto(){
        this.setDeletado("F");
    }

    public Produto(Integer idProduto, String nome, String descricao, int quantidade, Tipos tipo, double valor, int idUsuario) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.valor = valor;
        this.idUsuario = idUsuario;
        this.setDeletado("F");
    }
}
