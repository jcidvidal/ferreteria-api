package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Operaciones CRUD para administradores del sistema")
public class AdminController {
    private final AdminService adminService;
    private final ProductoService productoService;

    public AdminController(AdminService adminService, ProductoService productoService) {
        this.adminService = adminService;
        this.productoService = productoService;
    }

    @Operation(summary = "Registrar un administrador")
    @ApiResponse(responseCode = "201", description = "Administrador registrado correctamente")
    @PostMapping
    public String register(@Valid @RequestBody Admin admin) throws ExecutionException, InterruptedException {
        adminService.register(admin);
        return "Admin registrado.";
    }

    @Operation(summary = "Listar todos los administradores")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida con éxito")
    @GetMapping
    public List<Admin> getAll() throws ExecutionException, InterruptedException {
        List<Admin> admins = adminService.getAll();
        return admins;
    }

    @Operation(summary = "Buscar administrador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin encontrado"),
            @ApiResponse(responseCode = "404", description = "Admin no encontrado")
    })
    @GetMapping("/{id}")
    public Admin get(@PathVariable String id) throws ExecutionException, InterruptedException {
        Admin admin = adminService.get(id);
        return admin;
    }

    @Operation(summary = "Eliminar administrador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrador eliminado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        adminService.delete(id);
        return "Admin eliminado.";
    }

    @PutMapping("/modificar-stock")
    public String modificarStock(
            @RequestParam String idAdmin,
            @RequestParam String idProducto,
            @RequestParam int cantidad) throws Exception {

        Persona usuario = adminService.get(idAdmin);
        Producto producto = productoService.get(idProducto);

        adminService.modificarStockSiEsAdmin(usuario, producto, cantidad);
        return "Stock modificado";
    }

}
