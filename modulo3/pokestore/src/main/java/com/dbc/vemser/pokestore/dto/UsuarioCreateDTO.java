package com.dbc.vemser.pokestore.dto;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioCreateDTO {

    @NotBlank
    @UniqueElements
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String pix;

    @NotNull
    private String nome;

    @NotNull
    private String endereco;

    @CPF
    @UniqueElements
    private String cpf;

    @NotBlank
    @Size(max = 250)
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    @Size(max = 12)
    private String telefone;

    @NotBlank
    private String deletado;

}
