package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.PedidoCreateDTO;
import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DocumentationPedido {

    @Operation(summary = "listar pedidos", description = "Lista todos os pedidos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<PedidoDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "criar novo pedido", description = "Cria novo pedido no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody @Valid PedidoCreateDTO pedido) throws RegraDeNegocioException;

    @Operation(summary = "modificar um pedido selecionado por id", description = "Modifica um pedido no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modifica um pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer id,
                                            @RequestBody @Valid PedidoCreateDTO pedido) throws RegraDeNegocioException;

    @Operation(summary = "deletar um pedido selecionado por id", description = "Deleta um pedido no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta um pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> delete(@PathVariable("idPedido") Integer id) throws RegraDeNegocioException;

}
