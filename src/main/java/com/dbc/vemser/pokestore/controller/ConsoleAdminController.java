package com.dbc.vemser.pokestore.controller;


import com.dbc.vemser.pokestore.dto.UsuarioCargosDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/console-admin")
public class ConsoleAdminController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> atualizarCargos(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        log.info("Atualizando . . .");
        return ResponseEntity.ok(usuarioService.atualizarCargos(usuarioCargosDTO));
    }
}
