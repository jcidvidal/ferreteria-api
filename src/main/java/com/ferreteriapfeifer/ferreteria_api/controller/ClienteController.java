package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public String register(@RequestBody Cliente cliente) throws ExecutionException, InterruptedException {
        clienteService.register(cliente);
        return "Cliente registrado.";
    }

    @GetMapping("/{id}")
    public Cliente get(@PathVariable String id) throws ExecutionException, InterruptedException {
        Cliente cliente = clienteService.get(id);
        return cliente;
    }
    @GetMapping
    public String loadinforest()  {

        return "client endpoint.";
    }


}