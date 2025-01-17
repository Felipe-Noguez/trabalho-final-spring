package com.dbc.vemser.pokestore.security;

import com.dbc.vemser.pokestore.dto.LoginDTO;
import com.dbc.vemser.pokestore.entity.CargoEntity;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String CHAVE_CARGOS = "CARGOS";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.duracaotoken}")
    private String duracao;

    @Value("${jwt.duracaotokensenha}")
    private String duracaoTokenSenha;

    public String getToken(UsuarioEntity usuarioEntity) {
        // por meio do usuário, gerar um token        OK

        Date dataAgora = new Date();
        Date duracaoToken = new Date(dataAgora.getTime() + Long.parseLong(duracao));

        List<String> cargoUsuario = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("pokestore")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(CHAVE_CARGOS, cargoUsuario)
                .setIssuedAt(dataAgora)
                .setExpiration(duracaoToken)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getTokenRecuperarSenha(UsuarioEntity usuarioEntity) {

        Date dataAgora = new Date();
        Date duracaoToken = new Date(dataAgora.getTime() + Long.parseLong(duracaoTokenSenha));

        List<String> cargoUsuario = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("pokestore")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(CHAVE_CARGOS, cargoUsuario)
                .setIssuedAt(dataAgora)
                .setExpiration(duracaoToken)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if(token == null) {
            return null;
        }

        Claims corpo = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();


        String userId = corpo.get(Claims.ID, String.class);
        List<String> cargos = corpo.get(CHAVE_CARGOS, List.class);

        List<SimpleGrantedAuthority> listCargos = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(userId, null, listCargos);
    }

    public String autenticarAcesso(LoginDTO loginDTO, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object principal = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
        return getToken(usuarioEntity);
    }
}
