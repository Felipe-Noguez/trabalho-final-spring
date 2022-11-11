package com.dbc.vemser.pokestore.security;

import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.duracao.token}")
    private String duracaoToken;

    public String getToken(UsuarioEntity usuarioEntity) {
        // FIXME por meio do usuário, gerar um token        OK
        LocalDate agoraLocalDate = LocalDate.now();
        Date dataAgora = Date.from(agoraLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate duraçãoLocalDate = LocalDate.now().plusDays(Long.parseLong(duracaoToken));
        Date duracaoToken = Date.from(duraçãoLocalDate.atStartOfDay((ZoneId.systemDefault())).toInstant());

        String jwtToken = Jwts.builder()
                .setIssuer("vemser-api-noguez")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .setIssuedAt(dataAgora)
                .setExpiration(duracaoToken)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return jwtToken;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        // FIXME validar se o token é válido e retornar o usuário se for válido     OK
        if(token == null) {
            return null;
        }
//        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
                                  //jti = ID
        String userId = chaves.get(Claims.ID, String.class);

        UsernamePasswordAuthenticationToken tokenObject = new UsernamePasswordAuthenticationToken(userId,
                null,
                Collections.emptyList());

        return tokenObject;
    }
}
