package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.AvaliacaoProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/avaliacao-produto")
public class AvaliacaoProdutoController {

    private  final AvaliacaoProdutoService avaliacaoProdutoService;

    @PostMapping
    public ResponseEntity<AvaliacaoProdutoDTO> cadastrarAvalicao(@RequestBody @Valid AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO, Integer idPedido) throws RegraDeNegocioException {
        log.info("Cadastro de avaliação em andamento . . .");
        AvaliacaoProdutoDTO avaliacaoProdutoDTO = avaliacaoProdutoService.cadastrarAvaliacao(avaliacaoProdutoCreateDTO, idPedido);
        log.info("Avaliação cadastrada com sucesso, obrigado!");
        return new ResponseEntity<>(avaliacaoProdutoDTO, HttpStatus.OK);
    }

    @GetMapping("/filtrar-por-nota-avaliacao")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> listarAvaliacaoPorNota(@RequestParam(required = false) Double notaAvaliacao) {
        log.info("Filtrando busca, aguarde . . .");
        return ResponseEntity.ok(avaliacaoProdutoService.filtrarPorNota(notaAvaliacao));
    }



}
