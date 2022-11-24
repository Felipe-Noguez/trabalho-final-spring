package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.UsuarioCargosDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface DocumentationConsoleAdmin {

    @Operation(summary = "Atualiza cargos de usuário", description = "Atualiza cargos de usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza cargos do usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cargos")
    public ResponseEntity<UsuarioDTO> atualizarCargos(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException;

    @Operation(summary = "Administrador desativa conta de usuario", description = "Administrador pode desativar conta de usuarios no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Desativação de usuario com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}/desativacao")
    public ResponseEntity<UsuarioDTO> desativarUsuario(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException;

}
