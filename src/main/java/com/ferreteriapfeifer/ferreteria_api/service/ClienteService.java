package com.ferreteriapfeifer.ferreteria_api.service;

import ch.qos.logback.core.net.server.Client;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public void register(Cliente cliente) throws ExecutionException, InterruptedException {
        clienteRepository.register(cliente);
    }
    public Cliente get(String clientId) throws ExecutionException, InterruptedException {
       return clienteRepository.get(clientId);
    }
}