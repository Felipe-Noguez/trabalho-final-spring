package com.dbc.vemser.pokestore.controller;


import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.ProdutoService;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "listar produtos", description = "Lista todos os produtos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<ProdutoDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return produtoService.listarProdutos();
    }

    @Operation(summary = "criar novo produto", description = "Criar novo produto no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produto) throws RegraDeNegocioException, BancoDeDadosException{
        log.info("Criando produto novo....");

        ProdutoDTO produtoDTO = produtoService.adicionarProduto(produto);

        log.info("Produto criado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

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
                                             @RequestBody @Valid ProdutoCreateDTO produtoAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando produto....");

        ProdutoDTO produtoDTO = produtoService.editarProduto(id, produtoAtualizar);

        log.info("Produto atualizado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @Operation(summary = "deletar um produto selecionado por id", description = "Deleta um produto selecionado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta um produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idProduto}") // localhost:1521/pessoa/10
    public ResponseEntity<ProdutoDTO> delete(@PathVariable("idProduto") Integer id) throws RegraDeNegocioException {
        log.info("Deletando o produto");

        produtoService.removerProduto(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }





}
