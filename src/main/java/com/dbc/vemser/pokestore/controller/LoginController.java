package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.LoginDTO;
import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.security.TokenService;
import com.dbc.vemser.pokestore.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("Verificando autenticação . . .");
        return new ResponseEntity<>(tokenService.autenticarAcesso(loginDTO, authenticationManager), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastro(@Valid @RequestBody UsuarioCreateDTO usuario) throws RegraDeNegocioException {

        log.info("Cadastro de usuário em andamento . . .");
        UsuarioDTO usuarioDTO = usuarioService.adicionarUsuario(usuario);
        log.info("Cadastro realizado.");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueceuSenha(String email) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.enviaEmailParaRecuperarSenha(email));
    }

    @PostMapping("/trocar-senha")
    public ResponseEntity<String> novaSenha(String senha) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.atualizaSenha(senha));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> verificarLogin() throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.getLoggedUser());
    }
}