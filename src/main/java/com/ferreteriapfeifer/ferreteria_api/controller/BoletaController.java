package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.DetalleRequestDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/boleta")
@Tag(name = "Boleta Controller", description = "Operaciones relacionadas con las boletas generadas antes de la compra")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @Operation(summary = "Registrar una nueva boleta")
    @ApiResponse(responseCode = "201", description = "Boleta registrada correctamente")
    @PostMapping
    public String registrarBoleta(@Valid @RequestBody Boleta boleta) throws ExecutionException, InterruptedException {
        boleta.setIdBoleta(UUID.randomUUID().toString());
        boleta.setEstado("abierta");
        boletaService.registrarBoleta(boleta);
        return "Boleta registrada.";
    }

    @Operation(summary = "Obtener todas las boletas")
    @ApiResponse(responseCode = "200", description = "Lista de boletas obtenida")
    @GetMapping
    public List<Boleta> obtenerBoletas() throws ExecutionException, InterruptedException {
        return boletaService.obtenerBoletas();
    }

    @Operation(summary = "Obtener boleta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    @GetMapping("/{id}")
    public Boleta obtenerIdBoleta(@PathVariable String id) throws ExecutionException, InterruptedException {
        return boletaService.obtenerIdBoleta(id);
    }

    @Operation(summary = "Eliminar boleta por ID")
    @ApiResponse(responseCode = "204", description = "Boleta eliminada correctamente")
    @DeleteMapping("/{id}")
    public String eliminarBoleta(@PathVariable String id) throws ExecutionException, InterruptedException {
        boletaService.eliminarBoleta(id);
        return "Boleta eliminada.";
    }

    @Operation(summary = "Calcular total de una boleta")
    @PutMapping("/{idBoleta}/calcular-total")
    public String calcularTotal(@PathVariable String idBoleta) throws ExecutionException, InterruptedException {
        boletaService.calcularTotal(idBoleta);
        return "Total recalculado correctamente.";
    }

    @Operation(summary = "Cerrar una boleta")
    @PutMapping("/{idBoleta}/cerrar")
    public String cerrarBoleta(@PathVariable String idBoleta) throws ExecutionException, InterruptedException {
        boletaService.cerrarBoleta(idBoleta);
        return "Boleta cerrada correctamente.";
    }

    @Operation(summary = "Agregar un producto a la boleta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto agregado a la boleta correctamente"),
            @ApiResponse(responseCode = "400", description = "Boleta no válida o cerrada")
    })
    @PutMapping("/{id}/agregar-detalle")
    public ResponseEntity<String> agregarDetalle(
            @PathVariable String id,
            @Valid @RequestBody DetalleRequestDTO requestDTO) {
        try {
            boletaService.agregarDetalle(id, requestDTO.getIdProducto(), requestDTO.getCantidad());
            return ResponseEntity.ok("Detalle agregado a la boleta correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }
}
