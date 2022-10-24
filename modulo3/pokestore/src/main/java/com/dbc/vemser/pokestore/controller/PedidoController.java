package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.PedidoCreateDTO;
import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.exceptions.BancoDeDadosException;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.PedidoService;
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
@RequestMapping("/pedido")

public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return pedidoService.listarPedido();
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody @Valid PedidoCreateDTO pedido) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando novo pedido. . .");

        PedidoDTO pedidoDTO = pedidoService.adicionarPedido(pedido);

        log.info("Pedido criado com sucesso!");
        return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer id,
                                           @RequestBody @Valid PedidoCreateDTO pedido) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando pedido.... ");

        PedidoDTO pedidoDTO = pedidoService.editarPedido(id, pedido);

        log.info("Pedido editado com sucesso! ");

        return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<PedidoDTO> delete(@PathVariable("idPedido") Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Deletando o pedido");

        pedidoService.removerPedido(id);

        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }

}
