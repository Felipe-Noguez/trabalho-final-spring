package com.dbc.vemser.pokestore.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario {

    private Integer idUsuario;
    private String email;
    private String senha;
    private String pix;
    private String nome;
    private String endereco;
    private String cpf;
    private String cidade;
    private String estado;
    private String telefone;
    private String deletado = "F";

}




