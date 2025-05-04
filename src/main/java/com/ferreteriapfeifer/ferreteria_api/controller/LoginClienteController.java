package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/login/cliente")
public class LoginClienteController {

    private final ClienteService clienteService;
    private final JwtUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    public LoginClienteController(ClienteService clienteService, JwtUtil jwtUtil, PasswordUtil passwordUtil) {
        this.clienteService = clienteService;
        this.jwtUtil = jwtUtil;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping
    public ResponseEntity<?> loginCliente(@RequestParam String email, @RequestParam String contrasena)
            throws ExecutionException, InterruptedException {

        for (Cliente cliente : clienteService.obtenerClientes()) {
            if (cliente.getEmail().equals(email) && passwordUtil.matches(contrasena, cliente.getContrasena())) {
                String token = jwtUtil.generateToken( "CLIENTE",cliente);
                return ResponseEntity.ok("Bearer " + token);
            }
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}