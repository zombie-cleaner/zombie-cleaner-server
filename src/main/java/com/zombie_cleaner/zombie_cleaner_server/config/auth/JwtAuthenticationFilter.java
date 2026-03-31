package com.zombie_cleaner.zombie_cleaner_server.config.auth;

import com.zombie_cleaner.zombie_cleaner_server.entities.User;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver)
    {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");

        if(authHeader!= null && authHeader.startsWith("Bearer")){
            try{

            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);

            User userDetails = userDetailsService.loadUserByEmail(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (ExpiredJwtException  ex){
                exceptionResolver.resolveException(request, response, null, new AuthenticationException(ex.getMessage()));
                return;
            }
            catch (io.jsonwebtoken.security.SignatureException ex){
                exceptionResolver.resolveException(request, response, null, new AuthenticationException("Invalid JWT signature"));
                return;
            }
            catch (AuthorizationDeniedException ex){
                exceptionResolver.resolveException(request, response, null, new AuthenticationException("Unauthorized access"));
                return;
            }
            catch (JwtException ex){
                exceptionResolver.resolveException(request, response, null, new AuthenticationException("JWT error: " + ex.getMessage()));
                return;
            }
            catch (Exception ex){
                exceptionResolver.resolveException(request, response, null, new AuthenticationException("Authentication error: " + ex.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

