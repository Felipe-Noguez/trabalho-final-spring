package com.dbc.vemser.pokestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CupomCreateDTO {

    @Schema(description = "Valor de desconto que o cupom proporciona")
    private Double preco;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate dataVencimento;

}
