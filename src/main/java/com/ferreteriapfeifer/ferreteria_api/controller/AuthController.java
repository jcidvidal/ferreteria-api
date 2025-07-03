package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y generación de token JWT.")
public class AuthController {

    private final AdminService adminService;
    private final ClienteService clienteService;
    private final JwtUtil jwtUtil;

    public AuthController(AdminService adminService, ClienteService clienteService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.clienteService = clienteService;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN adaptado para Firebase
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) throws ExecutionException, InterruptedException {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String emailFirebase = decodedToken.getEmail();

            Optional<? extends Persona> usuarioOptional = buscarUsuarioPorEmail(emailFirebase);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(401).body("Usuario no encontrado.");
            }

            Persona persona = usuarioOptional.get();
            String token = jwtUtil.generateToken(persona.getRol(), persona);

            return ResponseEntity.ok(new TokenResponse(token, persona.getRol()));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }

    // REGISTRO adaptado para Firebase
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String emailFirebase = decodedToken.getEmail();

            if (!emailFirebase.equals(request.getEmail())) {
                return ResponseEntity.status(401).body("El email del token no coincide con el solicitado.");
            }

            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setEmail(request.getEmail());
            nuevoCliente.setRol(request.getRole().toUpperCase());
            nuevoCliente.setContrasena(""); // No usamos contraseña

            clienteService.registrarCliente(nuevoCliente);

            return ResponseEntity.ok("Usuario registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }

    // Método auxiliar simplificado
    private Optional<? extends Persona> buscarUsuarioPorEmail(String email) throws ExecutionException, InterruptedException {
        return clienteService.obtenerClientes().stream()
                .filter(cliente -> cliente.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Clases auxiliares
    @Data
    public static class RegisterRequest {
        @Email
        private String email;

        @NotBlank
        private String role;
    }

    @Data
    public static class TokenResponse {
        private final String token;
        private final String rol;
    }
}
