package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.repository.CompraRepository;
import com.google.cloud.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    @Autowired
    private GenerarPdfService generarPdfService;

    public CompraService(CompraRepository compraRepository, GenerarPdfService generarPdfService) {
        this.compraRepository = compraRepository;
        this.generarPdfService = generarPdfService;
    }

    public void registrarCompra(Compra compra) throws ExecutionException, InterruptedException {
        compra.setIdCompra(UUID.randomUUID().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaFormateada = LocalDateTime.now().format(formatter);
        compra.setFechaPago(fechaFormateada);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaFormateada = LocalDateTime.now().format(formatter);
        compra.setFechaPago(fechaFormateada);
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

        StringBuilder comprobante = new StringBuilder();
        comprobante.append("======= COMPROBANTE DE COMPRA =======\n");
        comprobante.append("ID Compra: ").append(compra.getIdCompra()).append("\n");
        comprobante.append("Fecha: ").append(compra.getFechaPago()).append("\n");
        comprobante.append("Cliente: ").append(compra.getCliente().getNombre()).append("\n");
        comprobante.append("Email: ").append(compra.getCliente().getEmail()).append("\n");
        comprobante.append("Teléfono: ").append(compra.getCliente().getTelefono()).append("\n");
        comprobante.append("Método de Pago: ").append(compra.getMetodoPago()).append("\n");
        comprobante.append("Estado: ").append(compra.getEstadoPago()).append("\n");
        comprobante.append("Monto Total: $").append(compra.getMontoPagado()).append("\n");
        comprobante.append("=====================================");

        return comprobante.toString();
    }

    public byte[] obtenerPDFComprobante(String idCompra) throws Exception {
        Compra compra = compraRepository.obtenerIdCompra(idCompra);

        if (compra == null) {
            throw new IllegalArgumentException("Compra no encontrada.");
        }

        return generarPdfService.generarComprobantePDF(compra);
    }

}
