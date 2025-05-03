package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente Controller", description = "Operaciones CRUD relacionadas con los clientes")

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Registrar un cliente")
    @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente")
    @PostMapping
    public String registrarCliente(@Valid @RequestBody Cliente cliente) throws ExecutionException, InterruptedException {
        cliente.setIdCliente(UUID.randomUUID().toString());
        //TODO: Hashear contraseña
        clienteService.registrarCliente(cliente);
        return "Cliente registrado.";
    }

    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
    @GetMapping
    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        List<Cliente> clientes = clienteService.obtenerClientes();
        return clientes;
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public Cliente obtenerIdCliente(@PathVariable String idCliente) throws ExecutionException, InterruptedException {
        Cliente cliente = clienteService.obtenerIdCliente(idCliente);
        return cliente;
    }

    @Operation(summary = "Eliminar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable String idCliente) throws ExecutionException, InterruptedException {
        clienteService.eliminarCliente(idCliente);
        return "Cliente eliminado.";
    }


}