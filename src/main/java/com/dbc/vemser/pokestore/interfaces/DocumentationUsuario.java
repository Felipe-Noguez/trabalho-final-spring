package com.dbc.vemser.pokestore.interfaces;


import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DocumentationUsuario {

    @Operation(summary = "listar usuarios de forma paginada", description = "Lista usuarios de acordo com a paginacao desejada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista paginada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuarios-paginados")
    public PageDTO<UsuarioDTO> listarUsuariosPaginados(Integer pagina, Integer numeroPaginas);

    @Operation(summary = "listar usuarios", description = "Lista todos os usuarios do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista usuarios"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping// localhost:1521/usuario
    public List<UsuarioDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "criar novo usuario", description = "Cria novo usuario no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping //localhost:1521/usuario
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuario) throws RegraDeNegocioException,BancoDeDadosException;

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
                                             @RequestBody @Valid UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException, BancoDeDadosException;
    @Operation(summary = "deletar usuario selecionado por id", description = "Deleta usuario selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta usuario selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}") // localhost:1521/pessoa/10
    public ResponseEntity<UsuarioDTO> delete(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException, BancoDeDadosException;


}
