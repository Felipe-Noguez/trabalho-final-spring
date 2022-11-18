package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.PagamentoCreateDTO;
import com.dbc.vemser.pokestore.dto.PagamentoDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criar(@RequestBody @Valid PagamentoCreateDTO pagamentoCreateDTO) throws RegraDeNegocioException {

        log.info("Criando pagamento novo....");
        PagamentoDTO pagamentoDTO = pagamentoService.criarPagamento(pagamentoCreateDTO);
        log.info("Pagamento criado com sucesso!");

        return new ResponseEntity<>(pagamentoDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listar(){
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @GetMapping("/media-vendas")
    public ResponseEntity<String> mostrarMediaVendasDoMes(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal){
        return ResponseEntity.ok(pagamentoService.mostrarMediaVendas(dataInicial, dataFinal));
    }
}
