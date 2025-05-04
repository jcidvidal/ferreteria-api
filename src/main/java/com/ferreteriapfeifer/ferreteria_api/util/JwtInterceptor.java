package com.ferreteriapfeifer.ferreteria_api.util;


import com.ferreteriapfeifer.ferreteria_api.annotations.SoloAdmin;
import com.ferreteriapfeifer.ferreteria_api.annotations.SoloCliente;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final AdminService adminService;
    private final ClienteService clienteService;

    public JwtInterceptor(JwtUtil jwtUtil, AdminService adminService, ClienteService clienteService) {
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
        this.clienteService = clienteService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token JWT faltante o malformado.");
            return false;
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            Claims claims = jwtUtil.getClaims(token);
            String rol = claims.get("rol", String.class);
            String email = claims.getSubject();

            // Validar existencia real en DB
            if ("ADMIN".equalsIgnoreCase(rol)) {
                if (!adminService.existePorEmail(email)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Administrador no encontrado.");
                    return false;
                }
            } else if ("CLIENTE".equalsIgnoreCase(rol)) {
                if (!clienteService.existePorEmail(email)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Cliente no encontrado.");
                    return false;
                }
            }

            // Validar anotaciones personalizadas
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethod().isAnnotationPresent(SoloAdmin.class) || method.getBeanType().isAnnotationPresent(SoloAdmin.class)) {
                if (!"ADMIN".equalsIgnoreCase(rol)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Requiere rol ADMIN.");
                    return false;
                }
            }

            if (method.getMethod().isAnnotationPresent(SoloCliente.class) || method.getBeanType().isAnnotationPresent(SoloCliente.class)) {
                if (!"CLIENTE".equalsIgnoreCase(rol)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Requiere rol CLIENTE.");
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token JWT inválido o expirado.");
            return false;
        }
    }
}