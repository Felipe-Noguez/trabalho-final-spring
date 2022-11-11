package com.dbc.vemser.pokestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String pix;
    private String cpf;
    private String telefone;

    private List<CargoDto> cargos = new ArrayList<>();

}
