package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.AdminDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String registrarAdmin(@Valid @RequestBody Admin admin) throws ExecutionException, InterruptedException {
        adminService.registrarAdmin(admin);
        return "Admin registrado.";
    }


    @Operation(summary = "Verificar acceso exclusivo de administrador")
    @ApiResponse(responseCode = "200", description = "Acceso validado para ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/solo-admin")
    public ResponseEntity<String> testSoloAdmin() {
        return ResponseEntity.ok("Solo un admin autenticado puede ver esto.");
    }

    @Operation(summary = "Listar todos los administradores")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida con éxito")
    @GetMapping
    public List<AdminDTO> obtenerAdmins() throws ExecutionException, InterruptedException {
        return adminService.obtenerAdmins().stream()
                .map(adminService::toDTO)
                .toList();
    }

    @Operation(summary = "Buscar administrador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin encontrado"),
            @ApiResponse(responseCode = "404", description = "Admin no encontrado")
    })
    @GetMapping("/{idAdmin}")
    public ResponseEntity<AdminDTO> obtenerIdAdmin(@PathVariable String idAdmin) throws ExecutionException, InterruptedException {
        Admin admin = adminService.obtenerIdAdmin(idAdmin);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adminService.toDTO(admin));
    }


    @Operation(summary = "Eliminar administrador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrador eliminado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @DeleteMapping("/{idAdmin}")
    public String eliminarAdmin(@PathVariable String idAdmin) throws ExecutionException, InterruptedException {
        adminService.eliminarAdmin(idAdmin);
        return "Admin eliminado.";
    }


    @Operation(summary = "Modificar stock de un producto (requiere rol ADMIN)")
    @ApiResponse(responseCode = "200", description = "Stock modificado exitosamente")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/modificar-stock")
    public String modificarStock(
            @RequestParam String idAdmin,
            @RequestParam String idProducto,
            @RequestParam int cantidad) throws Exception {

        Persona usuario = adminService.obtenerIdAdmin(idAdmin);
        Producto producto = productoService.obtenerIdProducto(idProducto);

        adminService.modificarStockSiEsAdmin(usuario, producto, cantidad);
        return "Stock modificado";
    }
}
