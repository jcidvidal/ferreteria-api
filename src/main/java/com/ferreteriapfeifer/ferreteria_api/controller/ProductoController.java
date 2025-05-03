package com.ferreteriapfeifer.ferreteria_api.controller;


import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import com.ferreteriapfeifer.ferreteria_api.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/producto")
@Tag(name = "Producto Controller", description = "Operaciones CRUD para productos")
public class ProductoController {

    private final ProductoService productoService;
    private final AdminService adminService;

    public ProductoController(ProductoService productoService, AdminService adminService) {
        this.productoService = productoService;
        this.adminService = adminService;
    }

    @Operation(summary = "Registrar nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto registrado correctamente")
    @PostMapping
    public String registrarProducto(@Valid @RequestBody Producto producto) throws ExecutionException, InterruptedException {
        producto.setIdProducto(UUID.randomUUID().toString());
        productoService.registrarProducto(producto);
        return "Producto registrado.";
    }

    @Operation(summary = "Obtener todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida")
    @GetMapping
    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {
        return productoService.obtenerProductos();
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public Producto obtenerIdProducto(@PathVariable String id) throws ExecutionException, InterruptedException {
        return productoService.obtenerIdProducto(id);
    }

    @Operation(summary = "Eliminar producto por ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable String id) throws ExecutionException, InterruptedException {
        productoService.eliminarProducto(id);
        return "Producto eliminado.";
    }

    @Operation(summary = "Modificar stock de un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock modificado"),
            @ApiResponse(responseCode = "403", description = "Solo administradores pueden modificar el stock")
    })
    @PutMapping("/modificar-stock")
    public String modificarStock(
            @RequestParam String idAdmin,
            @RequestParam String idProducto,
            @RequestParam int cantidad) throws Exception {

        Persona usuario = adminService.obtenerIdAdmin(idAdmin);
        Producto producto = productoService.obtenerIdProducto(idProducto);

        adminService.modificarStockSiEsAdmin(usuario, producto, cantidad);
        return "Stock modificado.";
    }
}