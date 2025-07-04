package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Registra un cliente nuevo verificando antes que no exista
    public void registrarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        if (existePorEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente registrado con este correo: " + cliente.getEmail());
        }

        cliente.setIdCliente(UUID.randomUUID().toString());

        // 🔑 Cifrado de contraseña solo si no es vacía (integración con Firebase)
        if (cliente.getContrasena() != null && !cliente.getContrasena().isEmpty()) {
            cliente.setContrasena(passwordUtil.encode(cliente.getContrasena()));
        } else {
            cliente.setContrasena("");  // vacío explícito para usuarios Firebase
        }

        cliente.setRol("CLIENTE");
        clienteRepository.registrarCliente(cliente);
    }

    // Obtiene cliente por ID
    public Cliente obtenerIdCliente(String idCliente) throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerIdCliente(idCliente);
    }

    // Obtiene todos los clientes
    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerClientes();
    }

    // Elimina un cliente por ID
    public void eliminarCliente(String idCliente) throws ExecutionException, InterruptedException {
        clienteRepository.eliminarCliente(idCliente);
    }

    // Convierte Cliente a DTO
    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    // Verifica si ya existe un cliente con ese email
    public boolean existePorEmail(String email) throws ExecutionException, InterruptedException {
        return obtenerClientes().stream()
                .anyMatch(cliente -> cliente.getEmail().trim().equalsIgnoreCase(email.trim()));
    }

    // Método auxiliar para eliminar duplicados si es necesario
    public void eliminarClientesDuplicados() throws ExecutionException, InterruptedException {
        List<Cliente> todos = obtenerClientes();
        Map<String, Cliente> unicosPorEmail = new HashMap<>();

        for (Cliente cliente : todos) {
            unicosPorEmail.put(cliente.getEmail().toLowerCase(), cliente);
        }

        for (Cliente cliente : todos) {
            Cliente clienteUnico = unicosPorEmail.get(cliente.getEmail().toLowerCase());
            if (!cliente.getIdCliente().equals(clienteUnico.getIdCliente())) {
                eliminarCliente(cliente.getIdCliente());
            }
        }
    }
}
