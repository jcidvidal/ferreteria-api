package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.RegisterRequest;  
import com.ferreteriapfeifer.ferreteria_api.dto.TokenResponse;    
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y generación de token JWT con Firebase")
public class AuthController {

    private final ClienteService clienteService;

    public AuthController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // LOGIN con Firebase (recibe token JWT desde frontend)
    @Operation(summary = "Login de usuario con Firebase")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, token generado"),
        @ApiResponse(responseCode = "401", description = "Token inválido, expirado o usuario no existe")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        try {
            String idToken = authHeader.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String emailFirebase = decodedToken.getEmail();

            Optional<? extends Persona> usuarioOptional = buscarUsuarioPorEmail(emailFirebase);

                //Este mensaje es para ver si no encuentra ningun usuario retorna un " usuario no encontrado"
            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(400).body("Usuario no encontrado.");
            }

            Persona persona = usuarioOptional.get();
            String rol = persona.getRol();

            // Respondemos con el MISMO token de Firebase y el rol del usuario
            return ResponseEntity.ok(new TokenResponse(idToken, rol));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }

    // REGISTRO con Firebase
    @Operation(summary = "Registro de usuario validado por Firebase")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro exitoso"),
        @ApiResponse(responseCode = "401", description = "Token inválido o expirado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request,  // DTO de registro
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
            nuevoCliente.setRol(request.getRol().toUpperCase());
            nuevoCliente.setContrasena(""); // SIN contraseña interna (solo Firebase Auth)

            clienteService.registrarCliente(nuevoCliente);

            return ResponseEntity.ok("Usuario registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado: " + e.getMessage());
        }
    }

    // Buscar cliente por email
    private Optional<? extends Persona> buscarUsuarioPorEmail(String email) throws ExecutionException, InterruptedException {
        return clienteService.obtenerClientes().stream()
                .filter(cliente -> cliente.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
