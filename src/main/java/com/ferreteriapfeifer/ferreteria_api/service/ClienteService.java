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
    private final PasswordUtil passwordUtil;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
                          PasswordUtil passwordUtil) {
        this.clienteRepository = clienteRepository;
        this.passwordUtil = passwordUtil;
    }

    /**
     * Registra un cliente nuevo verificando antes que no exista
     */
    public void registrarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        if (existePorEmail(cliente.getEmail())) {
            throw new IllegalArgumentException(
                "Ya existe un cliente registrado con este correo: " + cliente.getEmail()
            );
        }

        cliente.setIdCliente(UUID.randomUUID().toString());

        // Cifrado de contraseña si existe
        if (cliente.getContrasena() != null && !cliente.getContrasena().isEmpty()) {
            cliente.setContrasena(passwordUtil.encode(cliente.getContrasena()));
        } else {
            cliente.setContrasena("");
        }

        cliente.setRol("CLIENTE");
        clienteRepository.registrarCliente(cliente);
    }

    /**
     * Obtiene un cliente por su ID
     */
    public Cliente obtenerIdCliente(String idCliente) throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerIdCliente(idCliente);
    }

    /**
     * Obtiene todos los clientes
     */
    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        return clienteRepository.obtenerClientes();
    }

    /**
     * Elimina un cliente por su ID
     */
    public void eliminarCliente(String idCliente) throws ExecutionException, InterruptedException {
        clienteRepository.eliminarCliente(idCliente);
    }

    /**
     * Convierte una entidad Cliente a su DTO
     */
    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    /**
     * Verifica si ya existe un cliente con ese email
     */
    public boolean existePorEmail(String email) throws ExecutionException, InterruptedException {
        return obtenerClientes().stream()
                .anyMatch(c -> c.getEmail().trim().equalsIgnoreCase(email.trim()));
    }

    /**
     * Elimina clientes duplicados basándose en el email
     */
    public void eliminarClientesDuplicados() throws ExecutionException, InterruptedException {
        List<Cliente> todos = obtenerClientes();
        Map<String, Cliente> unicosPorEmail = new HashMap<>();

        // Mantener sólo uno por email
        for (Cliente c : todos) {
            unicosPorEmail.put(c.getEmail().toLowerCase(), c);
        }

        // Eliminar los restantes
        for (Cliente c : todos) {
            Cliente unico = unicosPorEmail.get(c.getEmail().toLowerCase());
            if (!c.getIdCliente().equals(unico.getIdCliente())) {
                eliminarCliente(c.getIdCliente());
            }
        }
    }
}
