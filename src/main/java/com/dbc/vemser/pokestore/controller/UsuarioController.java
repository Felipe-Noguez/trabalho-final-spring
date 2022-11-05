package com.dbc.vemser.pokestore.controller;

import com.dbc.vemser.pokestore.dto.PageDTO;
import com.dbc.vemser.pokestore.dto.UsuarioCreateDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.dto.UsuarioRelatorioPedidoDTO;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import com.dbc.vemser.pokestore.interfaces.DocumentationUsuario;
import com.dbc.vemser.pokestore.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/usuario")
public class UsuarioController implements DocumentationUsuario {

    private final UsuarioService usuarioService;

    @Override
    @GetMapping("/usuarios-paginados")
    public PageDTO<UsuarioDTO> listarUsuariosPaginados(Integer pagina, Integer quantidadePorPagina) {
        return usuarioService.listarUsuariosPaginados(pagina, quantidadePorPagina);
    }

    @Override
    @GetMapping("/relatorio-usuario-pedido")
    public List<UsuarioRelatorioPedidoDTO> relatorioUsuariosPedido(@RequestParam(required = false)Integer idUsuario) {
        return usuarioService.listarRelatorioUsuarioPedido(idUsuario);
    }

//    @Override
//    @GetMapping("/relatorio-geral-usuario-")
//    public List<UsuarioRelatorioPedidoDTO> relatorioGeralUsuarios(@RequestParam(required = false)Integer idUsuario) {
//        return usuarioService.listarRelatorioGeralUsuario(idUsuario);
//    }

    @Override
    @GetMapping// localhost:1521/usuario
    public ResponseEntity<List<UsuarioDTO>> list() throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @Override
    @PostMapping //localhost:1521/usuario
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        log.info("Criando usuario novo....");

        UsuarioDTO usuarioDTO = usuarioService.adicionarUsuario(usuario);

        log.info("Usuario criado com sucesso!");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUsuario") Integer id,
                                             @RequestBody @Valid UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando usuário....");
        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuarioAtualizar);
        log.info("Usuário atualizado com sucesso!");

        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);

    }

    @Override
    @DeleteMapping("/{idUsuario}") // localhost:1521/pessoa/10
    public ResponseEntity<UsuarioDTO> delete(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException {

        log.info("Deletando a pessoa");
        usuarioService.remover(id);
        log.info("Deletado com sucesso!");

        return ResponseEntity.noContent().build();
    }

}
