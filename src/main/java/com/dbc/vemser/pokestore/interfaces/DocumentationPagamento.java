package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface DocumentationPagamento {

    @Operation(summary = "Criar pagamento", description = "Realiza o registro do pagamento no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PagamentoDTO> criar(@RequestBody @Valid PagamentoCreateDTO pagamentoCreateDTO) throws RegraDeNegocioException, JsonProcessingException;

    @Operation(summary = "Listar pagamento", description = "Realiza a listagem do pagamento no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pagamento listagem com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listar();

    @Operation(summary = "Listar pagamento com a média de vendas", description = "Listar pagamento com a média de vendas por período desejado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/media-vendas")
    public ResponseEntity<String> mostrarMediaVendasDoMes(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal);
}
