package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.service.BoletaService;
import com.ferreteriapfeifer.ferreteria_api.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/compra")
@Tag(name = "Compra Controller", description = "Operaciones relacionadas con las compras realizadas por los clientes")
public class CompraController {

    private final CompraService compraService;
    private final BoletaService boletaService;

    public CompraController(CompraService compraService, BoletaService boletaService) {
        this.compraService = compraService;
        this.boletaService = boletaService;
    }

    @Operation(summary = "Registrar una nueva compra")
    @ApiResponse(responseCode = "201", description = "Compra registrada exitosamente")
    @PostMapping
    public String register(@Valid @RequestBody Compra compra) throws ExecutionException, InterruptedException {
        compra.setIdCompra(UUID.randomUUID().toString());
        compra.setFechaPago(LocalDateTime.now());
        compraService.register(compra);
        return "Compra registrada correctamente.";
    }

    @Operation(summary = "Obtener todas las compras")
    @ApiResponse(responseCode = "200", description = "Compras obtenidas correctamente")
    @GetMapping
    public List<Compra> getAll() throws ExecutionException, InterruptedException {
        return compraService.getAll();
    }

    @Operation(summary = "Obtener una compra por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compra encontrada"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @GetMapping("/{id}")
    public Compra get(@PathVariable String id) throws ExecutionException, InterruptedException {
        return compraService.get(id);
    }

    @Operation(summary = "Eliminar una compra por ID")
    @ApiResponse(responseCode = "204", description = "Compra eliminada exitosamente")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        compraService.delete(id);
        return "Compra eliminada correctamente.";
    }

    @Operation(summary = "Generar compra desde boleta cerrada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compra generada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La boleta debe estar cerrada")
    })
    @PostMapping("/generar-desde-boleta/{idBoleta}")
    public ResponseEntity<String> generarDesdeBoleta(@PathVariable String idBoleta) throws Exception {
        Boleta boleta = boletaService.get(idBoleta);
        if (boleta == null) return ResponseEntity.notFound().build();

        Compra compra = compraService.generarCompraDesdeBoleta(boleta);
        return ResponseEntity.status(201).body("Compra generada con ID: " + compra.getIdCompra());
    }

    @Operation(summary = "Actualizar el método de pago de una compra")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @PutMapping("/{id}/metodo-pago")
    public ResponseEntity<String> actualizarMetodoPago(
            @PathVariable String id,
            @RequestParam String metodoPago) throws ExecutionException, InterruptedException {

        Compra compra = compraService.get(id);
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }

        compraService.actualizarMetodoPago(id, metodoPago);
        return ResponseEntity.ok("Método de pago actualizado correctamente.");
    }


    @Operation(summary = "Obtener comprobante de una compra")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comprobante generado correctamente"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @GetMapping("/{id}/comprobante")
    public ResponseEntity<String> obtenerComprobante(@PathVariable String id) throws ExecutionException, InterruptedException {
        Compra compra = compraService.get(id);
        if (compra == null) {
            return ResponseEntity.notFound().build();
        }
        String comprobante = compraService.obtenerComprobante(id);
        return ResponseEntity.ok(comprobante);
    }


}
