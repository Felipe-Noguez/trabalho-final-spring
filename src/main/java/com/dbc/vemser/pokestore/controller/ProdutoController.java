package com.dbc.vemser.pokestore.controller;


import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.interfaces.DocumentationProduto;
import com.dbc.vemser.pokestore.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
@Validated
public class ProdutoController implements DocumentationProduto {

    private final ProdutoService produtoService;

    @Override
    @GetMapping
    public ResponseEntity<PageDTO<ProdutoDTO>> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return ResponseEntity.ok(produtoService.listarProdutos(pagina, tamanho));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produto) throws RegraDeNegocioException{

        log.info("Criando produto novo....");
        ProdutoDTO produtoDTO = produtoService.adicionarProduto(produto);
        log.info("Produto criado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer id,
                                             @RequestBody @Valid ProdutoCreateDTO produtoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando produto....");
        ProdutoDTO produtoDTO = produtoService.editarProduto(id, produtoAtualizar);
        log.info("Produto atualizado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idProduto}") // localhost:1521/pessoa/10
    public ResponseEntity<ProdutoDTO> delete(@PathVariable("idProduto") Integer id) throws RegraDeNegocioException {

        log.info("Deletando o produto");
        produtoService.removerProduto(id);
        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }


}
