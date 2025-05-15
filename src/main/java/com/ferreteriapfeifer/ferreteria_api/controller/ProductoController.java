package com.ferreteriapfeifer.ferreteria_api.controller;


import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Registrar un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto registrado exitosamente")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String registrarProducto(@Valid @RequestBody Producto producto) throws ExecutionException, InterruptedException {
        productoService.registrarProducto(producto);
        return "Producto registrado correctamente.";
    }

    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable String id) throws ExecutionException, InterruptedException {
        return productoService.obtenerIdProducto(id);
    }

    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Listado exitoso")
    @GetMapping
    public List<Producto> obtenerTodos() throws ExecutionException, InterruptedException {
        return productoService.obtenerProductos();
    }

    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto eliminado")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable String id) throws ExecutionException, InterruptedException {
        productoService.eliminarProducto(id);
        return "Producto eliminado correctamente.";
    }
}