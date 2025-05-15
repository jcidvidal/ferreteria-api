package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.ferreteriapfeifer.ferreteria_api.service.LoginClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login/cliente")
public class LoginClienteController {

    private final LoginClienteService loginClienteService;

    public LoginClienteController(LoginClienteService loginClienteService) {
        this.loginClienteService = loginClienteService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> loginTest(@RequestBody Login dataCliente) throws Exception {
        String response = loginClienteService.loginTest(dataCliente);
        return ResponseEntity.ok(response);
    }
}