package com.dbc.vemser.pokestore.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotNull
    @Schema(example = "user")
    private String login;
    @NotNull
    @Schema(example = "123")
    private String senha;
}
