package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/login/admin")
public class LoginAdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    public LoginAdminController(AdminService adminService, JwtUtil jwtUtil, PasswordUtil passwordUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping
    public ResponseEntity<?> loginAdmin(@RequestParam String email, @RequestParam String contrasena)
            throws ExecutionException, InterruptedException {

        for (Admin admin : adminService.obtenerAdmins()) {
            if (admin.getEmail().equals(email) && passwordUtil.matches(contrasena, admin.getContrasena())) {
                String token = jwtUtil.generateToken( "ADMIN",admin);
                return ResponseEntity.ok("Bearer " + token);
            }
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}