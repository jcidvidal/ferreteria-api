package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.ferreteriapfeifer.ferreteria_api.service.LoginAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login/admin")
public class LoginAdminController {

    private final LoginAdminService loginAdminService;

    public LoginAdminController(LoginAdminService loginAdminService) {
        this.loginAdminService = loginAdminService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> loginTest(@RequestBody Login dataAdmin) throws Exception {
        String response = loginAdminService.loginTest(dataAdmin);
        return ResponseEntity.ok(response);
    }
}
