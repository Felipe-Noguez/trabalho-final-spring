package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.LoginDTO;
import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.security.TokenService;
import com.dbc.vemser.pokestore.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
        String token = tokenService.getToken(usuarioEntity);
        return token;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        log.info("Cadastro de usuário em andamento . . .");
        UsuarioDTO usuarioDTO = usuarioService.adicionarUsuario(usuario);
        log.info("Cadastro realizado.");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> verificarAuth() throws RegraDeNegocioException {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioService.getLoggedUser(), UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }
}
