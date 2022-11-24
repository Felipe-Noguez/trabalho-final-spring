package com.dbc.vemser.pokestore.interfaces;


import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.dto.UsuarioRelatorioGeralDTO;
import com.dbc.vemser.pokestore.dto.UsuarioRelatorioPedidoDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DocumentationUsuario {

    @Operation(summary = "Relatório de usuário", description = "Relatório de pedidos do usuarios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o relatório de pedido do usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no relatório")
            }
    )
    @GetMapping("/relatorio-usuario-pedido")
    public ResponseEntity<List<UsuarioRelatorioPedidoDTO>> relatorioUsuariosPedido(@RequestParam(required = false)Integer idUsuario);

    @Operation(summary = "listar usuarios de forma paginada", description = "Lista usuarios de acordo com a paginacao desejada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista paginada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuarios-paginados")
    public ResponseEntity<List<UsuarioRelatorioGeralDTO>> relatorioGeralUsuarios(@RequestParam(required = false)Integer idUsuario);

    @Operation(summary = "listar usuarios", description = "Lista todos os usuarios do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista usuarios"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping// localhost:1521/usuario
    public ResponseEntity<PageDTO<UsuarioDTO>> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "deletar usuario selecionado por id", description = "Deleta usuario selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta usuario selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}") // localhost:1521/pessoa/10
    public ResponseEntity<UsuarioDTO> delete(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException;


}
