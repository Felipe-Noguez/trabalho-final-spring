package com.dbc.vemser.pokestore.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class Usuario {

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

//    @CPF
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

    public Usuario(){
        this.setDeletado("F");
    }

    public Usuario(Integer id, String email, String senha, String pix, String nome, String endereco, String cpf, String cidade, String estado, String telefone) {
        this.idUsuario = id;
        this.email = email;
        this.senha = senha;
        this.pix = pix;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.setDeletado("F");
    }

    public String getDeletado() {
        return deletado;
    }

    public void setDeletado(String deletado) {
        this.deletado = deletado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", pix='" + pix + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cpf=" + cpf +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", telefone=" + telefone +
                ", deletado='" + deletado + '\'' +
                '}';
    }
}




