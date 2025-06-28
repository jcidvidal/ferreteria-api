package com.ferreteriapfeifer.ferreteria_api.jwtAuthFilter;

import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // --- EXCLUSIÓN DE ENDPOINTS PÚBLICOS ---
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // --- SI NO HAY TOKEN, SIGUE (PUEDE QUE LA RUTA SEA SEMI-PÚBLICA) ---
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // --- VALIDACIÓN DEL TOKEN ---
        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado");
            return;
        }

        // --- EXTRACCIÓN DE DATOS DEL USUARIO ---
        String username = jwtUtil.extractUsername(token);
        String rol = jwtUtil.extractRol(token);

        // --- ASIGNACIÓN DE AUTORIDADES PARA SPRING SECURITY ---
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // --- CONTINUAR CON LA CADENA DE FILTROS ---
        filterChain.doFilter(request, response);
    }

    // --- FUNCION PARA EXCLUIR ENDPOINTS PUBLICOS DE AUTENTICACION ---
    private boolean isPublicEndpoint(String path) {
        // ¡AQUÍ puedes añadir/quitar endpoints públicos a voluntad!
        return path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/register")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/") // página home si la tienes pública
                || path.startsWith("/favicon")
                ;
    }
}
