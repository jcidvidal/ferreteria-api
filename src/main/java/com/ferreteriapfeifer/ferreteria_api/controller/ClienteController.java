package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDto;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
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
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody Cliente cliente) throws ExecutionException, InterruptedException {
        clienteService.registrarCliente(cliente);
        return ResponseEntity.status(201).body("Cliente registrado.");
    }

    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> obtenerClientesDto() throws ExecutionException, InterruptedException {
        List<ClienteDto> clientes = clienteService.obtenerClientes().stream()
                .map(clienteService::toDTO)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public Cliente obtenerIdCliente(@PathVariable("id") String idCliente) throws ExecutionException, InterruptedException {
        return clienteService.obtenerIdCliente(idCliente);
    }

    @Operation(summary = "Eliminar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") String idCliente) throws ExecutionException, InterruptedException {
        clienteService.eliminarCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/depurar-duplicados")
    public ResponseEntity<String> depurarDuplicados() throws ExecutionException, InterruptedException {
        clienteService.eliminarClientesDuplicados();
        return ResponseEntity.ok("Clientes duplicados eliminados.");
    }
}
