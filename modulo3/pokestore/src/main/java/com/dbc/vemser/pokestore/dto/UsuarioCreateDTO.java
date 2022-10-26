package com.dbc.vemser.pokestore.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

@Data
public class UsuarioCreateDTO {

    @Email
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
    private String cpf;

    @NotNull
    @Size(max = 250)
    private String cidade;

    @NotNull
    private String estado;

    @NotNull
    @Size(max = 12)
    private String telefone;

    @NotNull
    private String deletado;

}
