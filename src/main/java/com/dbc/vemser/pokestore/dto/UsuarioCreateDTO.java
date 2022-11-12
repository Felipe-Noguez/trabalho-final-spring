package com.dbc.vemser.pokestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioCreateDTO {

    @Email
    @Schema(description = "Email do usuario", example = "@mail")
    private String email;

    @NotNull
    @Schema(description = "Senha do usuario")
    private String senha;

    @NotNull
    @Schema(description = "Pix do usuario",example = "1234564567")
    private String pix;

    @NotNull
    @Schema(description = "Nome do usuario", example = "Mangini")
    private String nome;

    @NotNull
    @Schema(description = "Endereço do usuario")
    private String endereco;

    @CPF
    @Schema(description = "CPF do usuario", example = "99988855521")
    private String cpf;

    @NotNull
    @Size(max = 250)
    @Schema(description = "Cidade do usuario")
    private String cidade;

    @NotNull
    @Schema(description = "Estado do usuario")
    private String estado;

    @NotNull
    @Size(max = 12)
    @Schema(description = "Telefone do usuario", example = "519874513")
    private String telefone;
}
