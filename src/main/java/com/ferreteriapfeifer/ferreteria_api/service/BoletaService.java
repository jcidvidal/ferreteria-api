package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.DetalleProducto;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.BoletaRepository;
import com.ferreteriapfeifer.ferreteria_api.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoService productoService;

    public BoletaService(BoletaRepository boletaRepository, ProductoService productoService) {
        this.boletaRepository = boletaRepository;
        this.productoService = productoService;
    }

    public void registrarBoleta(Boleta boleta) throws ExecutionException, InterruptedException {
        boletaRepository.registrarBoleta(boleta);
    }

    public Boleta obtenerIdBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        return boletaRepository.obtenerIdBoleta(idBoleta);
    }

    public List<Boleta> obtenerBoletas() throws ExecutionException, InterruptedException {
        return boletaRepository.obtenerBoletas();
    }

    public void eliminarBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        boletaRepository.eliminarBoleta(idBoleta);
    }

    public void calcularTotal(String idBoleta) throws ExecutionException, InterruptedException {
        Boleta boleta = boletaRepository.obtenerIdBoleta(idBoleta);
        if (boleta != null && boleta.getEstado().equalsIgnoreCase("abierta")) {
            boleta.calcularTotal();
            boletaRepository.registrarBoleta(boleta);
        } else {
            throw new IllegalStateException("No se puede calcular total: boleta cerrada o inexistente.");
        }
    }

    public void cerrarBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        Boleta boleta = boletaRepository.obtenerIdBoleta(idBoleta);
        if (boleta != null && boleta.getEstado().equalsIgnoreCase("abierta")) {
            boleta.cerrarBoleta();
            boletaRepository.registrarBoleta(boleta);
        } else {
            throw new IllegalStateException("No se puede cerrar: boleta ya está cerrada o no existe.");
        }
    }

    public void agregarDetalle(String idBoleta, String idProducto, int cantidad)
            throws ExecutionException, InterruptedException {

        Boleta boleta = boletaRepository.obtenerIdBoleta(idBoleta);
        if (boleta == null) {
            throw new IllegalArgumentException("Boleta no encontrada.");
        }
        if (!boleta.getEstado().equalsIgnoreCase("abierta")) {
            throw new IllegalStateException("No se puede modificar una boleta cerrada.");
        }

        Producto producto = productoService.obtenerIdProducto(idProducto);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        if (producto.getStock() < cantidad) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
        }


        DetalleProducto existente = boleta.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .orElse(null);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            existente.calcularSubtotal();
        } else {
            DetalleProducto detalle = new DetalleProducto(producto, cantidad, 0);
            detalle.calcularSubtotal();
            boleta.getDetalles().add(detalle);
        }

        producto.setStock(producto.getStock() - cantidad);
        productoService.registrarProducto(producto); // Actualizar Firestore

        boleta.calcularTotal();
        boletaRepository.registrarBoleta(boleta); // Guardar la boleta actualizada
    }
}
