package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.CupomCreateDTO;
import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.CupomService;
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

    private CupomService cupomService;

    @GetMapping
    public List<CupomDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return cupomService.listarCupons();
    }

    @PostMapping
    public ResponseEntity<CupomDTO> create(@RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando cupom novo....");

        CupomDTO cupomDTO = cupomService.adicionarCupom(cupom);

        log.info("Cupom criado com sucesso!");
        return new ResponseEntity<>(cupomDTO, HttpStatus.OK);
    }

    @PutMapping("/{idCupom}")
    public ResponseEntity<CupomDTO> update(@PathVariable("idCupom") Integer id,
                                           @RequestBody @Valid CupomCreateDTO cupom) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando cupom.... ");

        CupomDTO cupomDTO = cupomService.editarCupom(id, cupom);

        log.info("Cupom editado com sucesso! ");

        return new ResponseEntity<>(cupomDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<CupomDTO> delete(@PathVariable("idCupom") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Deletando a pessoa");

        cupomService.removerCupom(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }
}