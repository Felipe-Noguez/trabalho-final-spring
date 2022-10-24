package com.dbc.vemser.pokestore.controller;


import com.dbc.vemser.pokestore.dto.ProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.ProdutoDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.ProdutoService;
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

    @GetMapping
    public List<ProdutoDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return produtoService.listarProdutos();
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produto) throws RegraDeNegocioException, BancoDeDadosException{
        log.info("Criando produto novo....");

        ProdutoDTO produtoDTO = produtoService.adicionarProduto(produto);

        log.info("Produto criado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer id,
                                             @RequestBody @Valid ProdutoCreateDTO produtoAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando produto....");

        ProdutoDTO produtoDTO = produtoService.editarProduto(id, produtoAtualizar);

        log.info("Produto atualizado com sucesso!");

        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{idProduto}") // localhost:1521/pessoa/10
    public ResponseEntity<ProdutoDTO> delete(@PathVariable("idProduto") Integer id) throws RegraDeNegocioException {
        log.info("Deletando o produto");

        produtoService.removerProduto(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }





}
