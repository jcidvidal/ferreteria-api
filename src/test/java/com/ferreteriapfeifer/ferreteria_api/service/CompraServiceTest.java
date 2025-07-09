package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.repository.CompraRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CompraServiceTest {


    CompraService compraService;
    CompraRepository compraRepository;
    GenerarPdfService generarPdfService;

    @BeforeEach
    void setUp() {
        compraRepository = Mockito.mock(CompraRepository.class);
        generarPdfService = Mockito.mock(GenerarPdfService.class);
        compraService = new CompraService(compraRepository, generarPdfService);
    }

    @Test
    void registrarCompra()throws ExecutionException, InterruptedException {
        Compra compra = new Compra();
        compra.setIdCompra(UUID.randomUUID().toString());
        compra.setMontoPagado(1234);
        compraService.registrarCompra(compra);
        verify(compraRepository).registrarCompra(compra);
    }

    @Test
    void obtenerIdCompra()throws ExecutionException, InterruptedException {
        String id = "compra123";
        Compra compra = new Compra();
        compra.setIdCompra(id);

        when(compraRepository.obtenerIdCompra(id)).thenReturn(compra);

        Compra result = compraService.obtenerIdCompra(id);

        Assertions.assertEquals(compra, result);
    }

    @Test
    void obtenerCompras() throws ExecutionException, InterruptedException{
        List<Compra> compras = List.of(new Compra(), new Compra());

        when(compraRepository.obtenerCompras()).thenReturn(compras);

        List<Compra> result = compraService.obtenerCompras();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void eliminarCompra()throws ExecutionException, InterruptedException {
        String id = "compraABC";

        compraService.eliminarCompra(id);

        verify(compraRepository).eliminarCompra(id);
    }

    @Test
    void generarCompraDesdeBoleta()throws ExecutionException, InterruptedException {
        Boleta boleta = new Boleta();
        boleta.setIdBoleta("boleta001");
        boleta.setEstado("cerrada");
        boleta.setTotal(10000);
        boleta.setCliente(new Cliente());

        Compra resultado = compraService.generarCompraDesdeBoleta(boleta);

        assertNotNull(resultado);
        Assertions.assertEquals(boleta.getTotal(), resultado.getMontoPagado());
        Assertions.assertEquals("pendiente", resultado.getMetodoPago());

        verify(compraRepository).registrarCompra(resultado);
    }

    @Test
    void actualizarMetodoPago()throws ExecutionException, InterruptedException {
        String id = "compraXYZ";
        Compra compra = new Compra();
        compra.setIdCompra(id);
        compra.setMetodoPago("pendiente");

        when(compraRepository.obtenerIdCompra(id)).thenReturn(compra);

        compraService.actualizarMetodoPago(id, "Transferencia");

        Assertions.assertEquals("Transferencia", compra.getMetodoPago());
        verify(compraRepository).registrarCompra(compra);
    }


    @Test
    void obtenerComprobante()throws ExecutionException, InterruptedException {
        String id = "compra789";
        Compra compra = new Compra();
        compra.setIdCompra(id);

        when(compraRepository.obtenerIdCompra(id)).thenReturn(compra);

        String resultado = compraService.obtenerComprobante(id);

        Assertions.assertEquals("Comprobante generado para la compra: " + id, resultado);

    }
}