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

    public void registrarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        cliente.setIdCliente(UUID.randomUUID().toString());
        clienteRepository.registrarCliente(cliente);
    }

    public Cliente obtenerIdCliente(String idCliente) throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerIdCliente(idCliente);
    }

    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerClientes();
    }

    public void eliminarCliente(String idCliente) throws ExecutionException, InterruptedException {
        clienteRepository.eliminarCliente(idCliente);
    }

}