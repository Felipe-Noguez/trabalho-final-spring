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
    @Schema(description = "Email do usuario")
    private String email;

    @NotNull
    @Schema(description = "Senha do usuario")
    private String senha;

    @NotNull
    @Schema(description = "Pix do usuario")
    private String pix;

    @NotNull
    @Schema(description = "Nome do usuario")
    private String nome;

    @NotNull
    @Schema(description = "Endereço do usuario")
    private String endereco;

    @CPF
    @Schema(description = "CPF do usuario")
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
    @Schema(description = "Telefone do usuario")
    private String telefone;

    @NotNull
    @Schema(description = "Informação se o usuário está ativo")
    private String deletado;

}
