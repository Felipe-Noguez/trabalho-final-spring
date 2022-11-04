package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DocumentationProduto {

    @Operation(summary = "listar produtos de forma paginada", description = "Lista produtos de acordo com a paginação desejada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista paginada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produtos-paginados")
    public PageDTO<ProdutoDTO> listarProdutosPaginados(Integer pagina, Integer numeroPaginas);


    @Operation(summary = "listar produtos", description = "Lista todos os produtos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<ProdutoDTO> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "criar novo produto", description = "Criar novo produto no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produto) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "modificar um produto selecionado por id", description = "Modifica um produto selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modifica um produto selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer id,
                                             @RequestBody @Valid ProdutoCreateDTO produtoAtualizar) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "deletar um produto selecionado por id", description = "Deleta um produto selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta um produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idProduto}") // localhost:1521/pessoa/10
    public ResponseEntity<ProdutoDTO> delete(@PathVariable("idProduto") Integer id) throws RegraDeNegocioException;

}

