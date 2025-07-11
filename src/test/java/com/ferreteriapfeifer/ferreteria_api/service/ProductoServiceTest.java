package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ProductoServiceTest {


    ProductoService productoService;
    ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {

        productoRepository = Mockito.mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);

    }

    @Test
    void registrarProducto() throws ExecutionException, InterruptedException{


        Producto producto = new Producto();
        producto.setNombre("Martillo");
        producto.setPrecio(12);
        productoService.registrarProducto(producto);
        assertNotNull(producto.getIdProducto());
        verify(productoRepository).registrarProducto(producto);
    }

    @Test
    void obtenerIdProducto() throws ExecutionException, InterruptedException{

        String id = UUID.randomUUID().toString();
        Producto producto = new Producto();
        producto.setIdProducto(id);

        Mockito.when(productoRepository.obtenerIdProducto(id)).thenReturn(producto);

        Producto result = productoService.obtenerIdProducto(id);

        assertEquals(producto, result);

    }

    @Test
    void obtenerProductos() throws ExecutionException, InterruptedException{

        List<Producto> productos = List.of(new Producto(), new Producto());

        Mockito.when(productoRepository.obtenerProductos()).thenReturn(productos);

        List<Producto> result = productoService.obtenerProductos();

        assertEquals(2, result.size());
    }

    @Test
    void eliminarProducto() throws ExecutionException, InterruptedException{
        String id = "123";

        productoService.eliminarProducto(id);

        verify(productoRepository).eliminarProducto(id);
    }


}