package com.dbc.vemser.pokestore.controller;


import com.dbc.vemser.pokestore.dto.UsuarioCargosDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/console-admin")
public class ConsoleAdminController {

    private final UsuarioService usuarioService;

    @PostMapping("/cargos")
    public ResponseEntity<UsuarioDTO> atualizarCargos(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        log.info("Atualizando . . .");
        return ResponseEntity.ok(usuarioService.atualizarCargos(usuarioCargosDTO));
    }

    @PutMapping("/{idUsuario}/desativacao")
    public ResponseEntity<UsuarioDTO> desativarUsuario(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException {
        log.info("Desativando usuário....");
        UsuarioDTO usuarioDTO = usuarioService.desativarUsuario(id);
        log.info("Usuário desativado com sucesso!");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);

    }
}
