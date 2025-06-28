package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
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
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    public AuthController(AdminService adminService, ClienteService clienteService,
                          PasswordUtil passwordUtil, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.clienteService = clienteService;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Login de usuario (Admin o Cliente)",
            description = "Permite autenticarse con email y contraseña. Retorna un JWT si las credenciales son válidas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso, token generado"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o usuario no encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) throws ExecutionException, InterruptedException {
        Optional<? extends Persona> usuarioOptional = buscarUsuarioPorEmail(request.getEmail());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado.");
        }

        Persona persona = usuarioOptional.get();
        if (!passwordUtil.matches(request.getContrasena(), persona.getContrasena())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta.");
        }

        String token = jwtUtil.generateToken(persona.getRol(), persona);
        return ResponseEntity.ok(new TokenResponse(token, persona.getRol()));
    }

    private Optional<? extends Persona> buscarUsuarioPorEmail(String email) throws ExecutionException, InterruptedException {
        for (Admin admin : adminService.obtenerAdmins()) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(admin);
            }
        }
        for (Cliente cliente : clienteService.obtenerClientes()) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    @Operation(
            summary = "Registro de usuario validado por Firebase",
            description = "Registra un usuario luego de validar el token de Firebase."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro exitoso"),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            System.out.println("TOKEN RECIBIDO: " + idToken);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            System.out.println("TOKEN DECODED: " + decodedToken);

            String emailFirebase = decodedToken.getEmail();
            System.out.println("EMAIL EN TOKEN: " + emailFirebase);
            System.out.println("EMAIL EN REQUEST: " + request.getEmail());

            if (!emailFirebase.equals(request.getEmail())) {
                return ResponseEntity.status(401).body("El email del token no coincide con el email solicitado.");
            }

            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setEmail(request.getEmail());
            nuevoCliente.setRol(request.getRole().toUpperCase());
            nuevoCliente.setContrasena(passwordUtil.encode("contraseña_por_defecto_o_vacia"));

            clienteService.registrarCliente(nuevoCliente);

            return ResponseEntity.ok("Usuario registrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }

    @Data
    public static class LoginRequest {
        @Email
        private String email;

        @NotBlank
        private String contrasena;
    }

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
