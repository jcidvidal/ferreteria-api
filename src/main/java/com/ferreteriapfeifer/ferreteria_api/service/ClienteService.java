package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (existePorEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con este correo: " + cliente.getEmail());
        }
        cliente.setIdCliente(UUID.randomUUID().toString());
        cliente.setContrasena(passwordUtil.encode(cliente.getContrasena()));
        cliente.setRol("CLIENTE");
        clienteRepository.registrarCliente(cliente);
    }

    public Cliente obtenerIdCliente(String idCliente) throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerIdCliente(idCliente);
    }

    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        List<Cliente> clientes = clienteRepository.obtenerClientes();
        for (Cliente cliente : clientes) {
            System.out.println("Cliente cargado:");
            System.out.println("- Email: " + cliente.getEmail());
            System.out.println("- Contraseña: " + cliente.getContrasena());
        }
        return clientes;
    }

    public void eliminarCliente(String idCliente) throws ExecutionException, InterruptedException {
        clienteRepository.eliminarCliente(idCliente);
    }

    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    public boolean existePorEmail(String email) throws ExecutionException, InterruptedException {
        return obtenerClientes().stream()
                .anyMatch(cliente -> cliente.getEmail().trim().equalsIgnoreCase(email.trim()));
    }

    public void eliminarClientesDuplicados() throws ExecutionException, InterruptedException {
        List<Cliente> todos = obtenerClientes();
        Map<String, Cliente> unicosPorEmail = new HashMap<>();

        for (Cliente cliente : todos) {
            unicosPorEmail.put(cliente.getEmail().toLowerCase(), cliente);
        }

        for (Cliente cliente : todos) {
            Cliente clienteUnico = unicosPorEmail.get(cliente.getEmail().toLowerCase());
            if (!cliente.getIdCliente().equals(clienteUnico.getIdCliente())) {
                System.out.println("Eliminando duplicado: " + cliente.getEmail() + " → " + cliente.getIdCliente());
                eliminarCliente(cliente.getIdCliente());
            }
        }
    }
}
