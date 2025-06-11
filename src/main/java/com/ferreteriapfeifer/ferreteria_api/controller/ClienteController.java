package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.service.ClienteService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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




    @Operation(summary = "Verificar acceso exclusivo de cliente")
    @ApiResponse(responseCode = "200", description = "Acceso validado para CLIENTE")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping("/solo-cliente")
    public ResponseEntity<String> testSoloCliente() {
        return ResponseEntity.ok("✅ Solo un cliente autenticado puede ver esto.");
    }

    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerClientesDto() throws ExecutionException, InterruptedException {
        List<ClienteDTO> clientes = clienteService.obtenerClientes().stream()
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
    public ResponseEntity<ClienteDTO> obtenerIdCliente(@PathVariable("id") String idCliente) throws ExecutionException, InterruptedException {
        Cliente cliente = clienteService.obtenerIdCliente(idCliente);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.toDTO(cliente));
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



    @Operation(summary = "Eliminar clientes duplicados por email")
    @ApiResponse(responseCode = "200", description = "Duplicados eliminados exitosamente")
    @GetMapping("/depurar-duplicados")
    public ResponseEntity<String> clienteDuplicados() throws ExecutionException, InterruptedException {
        clienteService.eliminarClientesDuplicados();
        return ResponseEntity.ok("Clientes duplicados eliminados.");
    }
}
