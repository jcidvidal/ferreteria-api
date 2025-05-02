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

    public void register(Compra compra) throws ExecutionException, InterruptedException {
        compraRepository.register(compra);
    }

    public Compra get(String idCompra) throws ExecutionException, InterruptedException {
        return compraRepository.get(idCompra);
    }

    public List<Compra> getAll() throws ExecutionException, InterruptedException {
        return compraRepository.getAll();
    }

    public void delete(String idCompra) throws ExecutionException, InterruptedException {
        compraRepository.delete(idCompra);
    }

    public Compra generarCompraDesdeBoleta(Boleta boleta) throws ExecutionException, InterruptedException {
        if (!"cerrada".equalsIgnoreCase(boleta.getEstado())) {
            throw new IllegalStateException("La boleta debe estar cerrada para generar la compra.");
        }

        Compra compra = new Compra();
        compra.setIdCompra(UUID.randomUUID().toString());
        compra.setFechaPago(LocalDateTime.now());
        compra.setCliente(boleta.getCliente());
        compra.setMetodoPago("pendiente"); // Puedes dejarlo dinámico luego
        compra.setMontoPagado(boleta.getTotal());

        compraRepository.register(compra);
        return compra;
    }

    public void actualizarMetodoPago(String idCompra, String metodoPago) throws ExecutionException, InterruptedException {
        Compra compra = compraRepository.get(idCompra);
        if (compra == null) {
            throw new IllegalArgumentException("Compra no encontrada.");
        }

        compra.setMetodoPago(metodoPago);
        compraRepository.register(compra); // Reescribe la compra con el nuevo método de pago
    }

    public String obtenerComprobante(String idCompra) throws ExecutionException, InterruptedException {
        Compra compra = compraRepository.get(idCompra);
        if (compra == null) {
            throw new IllegalArgumentException("Compra no encontrada.");
        }
        return compra.generarComprobante();
    }


}
