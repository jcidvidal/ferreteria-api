package com.ferreteriapfeifer.ferreteria_api.controller;


import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/verificar")
    public ResponseEntity<?> verificarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (!authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Token inválido (falta Bearer)");
            }

            String token = authHeader.replace("Bearer ", "");
            Claims claims = jwtUtil.getClaims(token);

            String email = claims.getSubject();
            String rol = claims.get("rol", String.class);

            return ResponseEntity.ok("Token válido. Email: " + email + ", Rol: " + rol);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado.");
        }
    }
}