package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.BoletaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private BoletaService boletaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRegistrarBoleta() throws ExecutionException, InterruptedException {
        Boleta boleta = new Boleta();
        boleta.setIdBoleta(UUID.randomUUID().toString());
        boleta.setEstado("abierta");

        boletaService.registrarBoleta(boleta);

        verify(boletaRepository, times(1)).registrarBoleta(boleta);
    }

    @Test
    void testCalcularTotal_BoletaAbierta() throws ExecutionException, InterruptedException {
        // Arrange
        String boletaId = "b-001";
        Producto producto1 = new Producto();
        producto1.setPrecio(5000);

        Producto producto2 = new Producto();
        producto2.setPrecio(3000);

        var detalle1 = new com.ferreteriapfeifer.ferreteria_api.model.DetalleProducto(producto1, 2, 0); 
        var detalle2 = new com.ferreteriapfeifer.ferreteria_api.model.DetalleProducto(producto2, 3, 0);

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(boletaId);
        boleta.setEstado("abierta");
        boleta.setDetalles(java.util.List.of(detalle1, detalle2));

        when(boletaRepository.obtenerIdBoleta(boletaId)).thenReturn(boleta);


        boletaService.calcularTotal(boletaId);


        assertEquals(19000, boleta.getTotal());
        verify(boletaRepository).registrarBoleta(boleta);
    }

    @Test
    void testAgregarDetalle_nuevoProductoEnBoleta() throws Exception {

        String boletaId = "b-001";
        String productoId = "p-123";

        Producto producto = new Producto();
        producto.setIdProducto(productoId);
        producto.setNombre("Martillo");
        producto.setPrecio(5000);
        producto.setStock(10);

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(boletaId);
        boleta.setEstado("abierta");
        boleta.setDetalles(new java.util.ArrayList<>());

        when(boletaRepository.obtenerIdBoleta(boletaId)).thenReturn(boleta);
        when(productoService.obtenerIdProducto(productoId)).thenReturn(producto);


        boletaService.agregarDetalle(boletaId, productoId, 2);


        assertEquals(1, boleta.getDetalles().size());
        assertEquals(5000 * 2, boleta.getDetalles().get(0).getSubtotal());
        assertEquals(5000 * 2, boleta.getTotal());
        assertEquals(8, producto.getStock());

        verify(productoService).registrarProducto(producto);
        verify(boletaRepository).registrarBoleta(boleta);
    }


    @Test
    void testAgregarDetalle_productoYaExisteEnBoleta() throws Exception {

        String boletaId = "b-002";
        String productoId = "p-999";

        Producto producto = new Producto();
        producto.setIdProducto(productoId);
        producto.setNombre("Destornillador");
        producto.setPrecio(3000);
        producto.setStock(10);


        var detalleExistente = new com.ferreteriapfeifer.ferreteria_api.model.DetalleProducto(producto, 1, 3000);

        Boleta boleta = new Boleta();
        boleta.setIdBoleta(boletaId);
        boleta.setEstado("abierta");
        boleta.setDetalles(new java.util.ArrayList<>(List.of(detalleExistente)));

        when(boletaRepository.obtenerIdBoleta(boletaId)).thenReturn(boleta);
        when(productoService.obtenerIdProducto(productoId)).thenReturn(producto);


        boletaService.agregarDetalle(boletaId, productoId, 2);


        assertEquals(1, boleta.getDetalles().size());
        assertEquals(3, boleta.getDetalles().get(0).getCantidad());
        assertEquals(9000, boleta.getDetalles().get(0).getSubtotal());
        assertEquals(9000, boleta.getTotal());
        assertEquals(8, producto.getStock());

        verify(productoService).registrarProducto(producto);
        verify(boletaRepository).registrarBoleta(boleta);
    }


}
