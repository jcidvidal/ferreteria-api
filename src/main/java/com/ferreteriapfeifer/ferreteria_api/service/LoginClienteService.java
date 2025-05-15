package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.ferreteriapfeifer.ferreteria_api.repository.LoginClienteRepository;
import com.ferreteriapfeifer.ferreteria_api.util.JwtUtil;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginClienteService {

    private final LoginClienteRepository loginClienteRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    public LoginClienteService(LoginClienteRepository loginClienteRepository,
                               PasswordUtil passwordUtil,
                               JwtUtil jwtUtil) {
        this.loginClienteRepository = loginClienteRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    public String loginTest(Login dataCliente) throws Exception {
        Cliente cliente = loginClienteRepository.loginTest(dataCliente);
        boolean valid = passwordUtil.matches(dataCliente.getPassword().trim(), cliente.getContrasena());

        if (!valid) {
            return "Fallo";
        }

        return "Bearer " + jwtUtil.generateToken("CLIENTE", cliente);
    }
}
