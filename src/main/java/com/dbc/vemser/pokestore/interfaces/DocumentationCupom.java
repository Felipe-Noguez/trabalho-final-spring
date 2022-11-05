package com.dbc.vemser.pokestore.interfaces;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DocumentationCupom {

    @Operation(summary = "listar cupons", description = "Lista todos os cupons do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping// localhost:8080/cupom
    public ResponseEntity<List<CupomDTO>> list() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "criar novo cupom", description = "Cria novo cupomn")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo cupom"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping// localhost:8080/cupom
    public ResponseEntity<CupomDTO> create(@RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "modificar um cupom selecionado por id", description = "Modifica um cupom do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modifica um cupom selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCupom}")// localhost:8080/cupom/idCupom
    public ResponseEntity<CupomDTO> update(@PathVariable("idCupom") Integer id,
                                           @RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "deletar um cupom selecionado por id", description = "Deleta um cupom do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta um cupom selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCupom}") //localhost:8080/cupom/idCupom
    public ResponseEntity<CupomDTO> delete(@PathVariable("idCupom") Integer id) throws RegraDeNegocioException, BancoDeDadosException;


}
