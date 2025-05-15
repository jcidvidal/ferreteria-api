package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.ferreteriapfeifer.ferreteria_api.repository.LoginAdminRepository;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginAdminService {

    private final LoginAdminRepository loginAdminRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    public LoginAdminService(LoginAdminRepository loginAdminRepository,
                             PasswordUtil passwordUtil,
                             JwtUtil jwtUtil) {
        this.loginAdminRepository = loginAdminRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    public String loginTest(Login dataAdmin) throws Exception {
        Admin admin = loginAdminRepository.loginTest(dataAdmin);
        boolean valid = passwordUtil.matches(dataAdmin.getPassword().trim(), admin.getContrasena());

        if (!valid) {
            return "Fallo";
        }

        return "Bearer " + jwtUtil.generateToken("ADMIN", admin);
    }
}