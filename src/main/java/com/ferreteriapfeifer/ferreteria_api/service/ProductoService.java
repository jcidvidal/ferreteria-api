package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;


    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public void registrarProducto(Producto producto) throws ExecutionException, InterruptedException {
        producto.setIdProducto(UUID.randomUUID().toString());
        productoRepository.registrarProducto(producto);
    }

    public Producto obtenerIdProducto(String productoId) throws ExecutionException, InterruptedException {
        return productoRepository.obtenerIdProducto(productoId);
    }

    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {
        return productoRepository.obtenerProductos();
    }

    public void eliminarProducto(String productoId) throws ExecutionException, InterruptedException {
        productoRepository.eliminarProducto(productoId);
    }

}
