package com.dbc.vemser.pokestore.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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




