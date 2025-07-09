package com.ferreteriapfeifer.ferreteria_api.controller;


import com.ferreteriapfeifer.ferreteria_api.dto.LoginRequest;
import com.ferreteriapfeifer.ferreteria_api.dto.TokenResponse;
import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(new TokenResponse(token));
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





}