package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void register(Cliente cliente) throws ExecutionException, InterruptedException {
        cliente.setIdCliente(UUID.randomUUID().toString());
        clienteRepository.register(cliente);
    }

    public Cliente get(String clientId) throws ExecutionException, InterruptedException {
        return clienteRepository.get(clientId);
    }

    public List<Cliente> getAll() throws ExecutionException, InterruptedException {
        return clienteRepository.getAll();
    }

    public void delete(String clientId) throws ExecutionException, InterruptedException {
        clienteRepository.delete(clientId);
    }

}