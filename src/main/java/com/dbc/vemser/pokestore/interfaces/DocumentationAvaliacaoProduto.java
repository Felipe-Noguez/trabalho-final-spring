package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoCreateDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoDTO;
import com.dbc.vemser.pokestore.dto.AvaliacaoProdutoUltimaSemanaDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface DocumentationAvaliacaoProduto {

    @Operation(summary = "Cadastrar avaliação do produto", description = "Cadastra no MongoDB a avaliação do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de avaliação com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção durante o cadastro")
            }
    )
    @PostMapping
    public ResponseEntity<AvaliacaoProdutoDTO> cadastrarAvalicao(@RequestBody @Valid AvaliacaoProdutoCreateDTO avaliacaoProdutoCreateDTO, Integer idPedido) throws RegraDeNegocioException;

    @Operation(summary = "Listar avaliação do produto por nota", description = "Lista do MongoDB a avaliação do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listada a avaliação com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção durante a listagem")
            }
    )
    @GetMapping("/filtrar-por-nota-avaliacao")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> listarAvaliacaoPorNota(@RequestParam(required = false) Double notaAvaliacao) throws RegraDeNegocioException;

    @Operation(summary = "Listar avaliação dos produtos da última semana", description = "Lista do MongoDB a avaliação do produto da última semana")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listada a avaliação dos útlimos 7 dias com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção durante a listagem")
            }
    )
    @GetMapping("/filtrar-avaliacao-ultima-semana")
    public ResponseEntity<List<AvaliacaoProdutoUltimaSemanaDTO>> filtarAvaliacaoUltimaSemana() throws RegraDeNegocioException;

}
