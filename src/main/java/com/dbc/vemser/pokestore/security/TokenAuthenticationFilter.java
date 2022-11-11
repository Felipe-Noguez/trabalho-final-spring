package com.dbc.vemser.pokestore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // FIXME recuperar token do header                                  OK
        // FIXME recuperar usuário do token                                 OK
        // FIXME adicionar o usuário no contexto do spring security         OK
        String headerAuth = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken tokenObject = tokenService.isValid(headerAuth);

        SecurityContextHolder.getContext().setAuthentication(tokenObject);
//        if(tokenObject != null){
//            SecurityContextHolder.getContext().setAuthentication(tokenObject);
//        } else {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
        filterChain.doFilter(request, response);
    }
}
