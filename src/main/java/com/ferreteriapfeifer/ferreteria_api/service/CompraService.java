package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public void registrarCompra(Compra compra) throws ExecutionException, InterruptedException {
        compraRepository.registrarCompra(compra);
    }

    public Compra obtenerIdCompra(String idCompra) throws ExecutionException, InterruptedException {
        return compraRepository.obtenerIdCompra(idCompra);
    }

    public List<Compra> obtenerCompras() throws ExecutionException, InterruptedException {
        return compraRepository.obtenerCompras();
    }

    public void eliminarCompra(String idCompra) throws ExecutionException, InterruptedException {
        compraRepository.eliminarCompra(idCompra);
    }

    public Compra generarCompraDesdeBoleta(Boleta boleta) throws ExecutionException, InterruptedException {
        if (!"cerrada".equalsIgnoreCase(boleta.getEstado())) {
            throw new IllegalStateException("La boleta debe estar cerrada para generar la compra.");
        }

        Compra compra = new Compra();
        compra.setIdCompra(UUID.randomUUID().toString());
        compra.setFechaPago(LocalDateTime.now());
        compra.setCliente(boleta.getCliente());
        compra.setMetodoPago("pendiente");
        compra.setMontoPagado(boleta.getTotal());

        compraRepository.registrarCompra(compra);
        return compra;
    }

    public void actualizarMetodoPago(String idCompra, String metodoPago) throws ExecutionException, InterruptedException {
        Compra compra = compraRepository.obtenerIdCompra(idCompra);
        if (compra == null) {
            throw new IllegalArgumentException("Compra no encontrada.");
        }

        compra.setMetodoPago(metodoPago);
        compraRepository.registrarCompra(compra);
    }

    public String obtenerComprobante(String idCompra) throws ExecutionException, InterruptedException {
        Compra compra = compraRepository.obtenerIdCompra(idCompra);
        if (compra == null) {
            throw new IllegalArgumentException("Compra no encontrada.");
        }
        return compra.generarComprobante();
    }


}
