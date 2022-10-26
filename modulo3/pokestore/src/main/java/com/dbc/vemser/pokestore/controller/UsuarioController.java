package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "listar usuarios", description = "Lista todos os usuarios do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista usuarios"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping// localhost:1521/usuario
    public List<UsuarioDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return usuarioService.listar();
    }

    @Operation(summary = "criar novo usuario", description = "Cria novo usuario no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping //localhost:1521/usuario
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuario) throws RegraDeNegocioException,BancoDeDadosException{
        log.info("Criando usuario novo....");

        UsuarioDTO usuarioDTO = usuarioService.adicionarUsuario(usuario);

        log.info("Usuario criado com sucesso!");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Operation(summary = "modificar usuario selecionado por id", description = "Modifica um usuario selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modifica um usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUsuario") Integer id,
                                             @RequestBody @Valid UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException, BancoDeDadosException{
        log.info("Atualizando usuário....");

        UsuarioDTO usuarioDTO = usuarioService.editar(id,usuarioAtualizar);

        log.info("Usuário atualizado com sucesso!");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Operation(summary = "deletar usuario selecionado por id", description = "Deleta usuario selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta usuario selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}") // localhost:1521/pessoa/10
    public ResponseEntity<UsuarioDTO> delete(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Deletando a pessoa");

        usuarioService.remover(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }

}
