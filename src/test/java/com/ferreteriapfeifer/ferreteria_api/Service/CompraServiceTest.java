package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerarCompraDesdeBoleta_conBoletaCerrada() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");

        Boleta boleta = new Boleta();
        boleta.setIdBoleta("b-001");
        boleta.setEstado("cerrada");
        boleta.setCliente(cliente);
        boleta.setTotal(15000);


        Compra compra = compraService.generarCompraDesdeBoleta(boleta);


        assertNotNull(compra);
        assertEquals(15000, compra.getMontoPagado());
        assertEquals("pendiente", compra.getMetodoPago());
        assertEquals(cliente, compra.getCliente());
        assertNotNull(compra.getFechaPago());

        verify(compraRepository).registrarCompra(compra);
    }


    @Test
    void testGenerarCompraDesdeBoleta_boletaAbierta_lanzaExcepcion() {

        Boleta boleta = new Boleta();
        boleta.setEstado("abierta");


        assertThrows(IllegalStateException.class, () -> {
            compraService.generarCompraDesdeBoleta(boleta);
        });

        verifyNoInteractions(compraRepository);
    }


    @Test
    void testActualizarMetodoPago() throws Exception {

        String idCompra = "cmp-001";
        Compra compra = new Compra();
        compra.setIdCompra(idCompra);
        compra.setMetodoPago("pendiente");

        when(compraRepository.obtenerIdCompra(idCompra)).thenReturn(compra);


        compraService.actualizarMetodoPago(idCompra, "Tarjeta");


        assertEquals("Tarjeta", compra.getMetodoPago());
        verify(compraRepository).registrarCompra(compra);
    }


    @Test
    void testObtenerComprobante() throws Exception {

        String idCompra = "cmp-002";
        Compra compra = new Compra();
        compra.setIdCompra(idCompra);

        when(compraRepository.obtenerIdCompra(idCompra)).thenReturn(compra);


        String comprobante = compraService.obtenerComprobante(idCompra);


        assertEquals("Comprobante generado para la compra: " + idCompra, comprobante);
    }



    @Test
    void testObtenerComprobante_compraNoExiste_lanzaExcepcion() throws Exception {

        String idCompra = "cmp-003";
        when(compraRepository.obtenerIdCompra(idCompra)).thenReturn(null);


        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            compraService.obtenerComprobante(idCompra);
        });

        assertEquals("Compra no encontrada.", e.getMessage());
    }

}
