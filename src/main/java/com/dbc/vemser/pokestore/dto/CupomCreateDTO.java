package com.dbc.vemser.pokestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CupomCreateDTO {

    @Schema(description = "Valor de desconto que o cupom proporciona")
    private Double valor;

}
