package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.CupomService;
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
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cupom")
public class CupomController {

    private final CupomService cupomService;

    @Operation(summary = "listar cupons", description = "Lista todos os cupons do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping// localhost:8080/cupom
    public List<CupomDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return cupomService.listarCupons();
    }

    @Operation(summary = "criar novo cupom", description = "Cria novo cupomn")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria novo cupom"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping// localhost:8080/cupom
    public ResponseEntity<CupomDTO> create(@RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando cupom novo....");

        CupomDTO cupomDTO = cupomService.adicionarCupom(cupom);

        log.info("Cupom criado com sucesso!");
        return new ResponseEntity<>(cupomDTO, HttpStatus.OK);
    }

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
                                           @RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando cupom.... ");

        CupomDTO cupomDTO = cupomService.editarCupom(id, cupom);

        log.info("Cupom editado com sucesso! ");

        return new ResponseEntity<>(cupomDTO, HttpStatus.OK);
    }

    @Operation(summary = "deletar um cupom selecionado por id", description = "Deleta um cupom do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta um cupom selecionado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCupom}") //localhost:8080/cupom/idCupom
    public ResponseEntity<CupomDTO> delete(@PathVariable("idCupom") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Deletando a pessoa");

        cupomService.removerCupom(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }
}