package com.dbc.vemser.pokestore.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

@Data
public class UsuarioCreateDTO {

    //    @NotNull
//    @Positive
    private Integer idUsuario;

    //    @Email
    private String email;

    //    @NotNull
    private String senha;

    //    @NotBlank
    private String pix;

    //    @NotNull
    private String nome;

    //    @NotBlank
    private String endereco;

        @CPF
    private String cpf;

    //    @NotNull
    @Size(max = 250)
    private String cidade;

    //    @NotNull
    private String estado;

    //    @NotBlank
    @Size(max = 12)
    private String telefone;

    //    @NotBlank
    private String deletado;

}
