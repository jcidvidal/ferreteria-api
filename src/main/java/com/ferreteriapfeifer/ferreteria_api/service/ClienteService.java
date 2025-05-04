package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDto;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PasswordUtil passwordUtil;

    public void registrarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        cliente.setIdCliente(UUID.randomUUID().toString());
        cliente.setContrasena(passwordUtil.encode(cliente.getContrasena()));
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

    public ClienteDto toDTO(Cliente cliente) {
        ClienteDto dto = new ClienteDto();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    public boolean existePorEmail(String email) throws ExecutionException, InterruptedException {
        return obtenerClientes().stream().anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }

}