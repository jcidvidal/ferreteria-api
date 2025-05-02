package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.ClienteRepository;
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

    public void register(Producto producto) throws ExecutionException, InterruptedException {
        producto.setIdProducto(UUID.randomUUID().toString());
        productoRepository.register(producto);
    }

    public Producto get(String productoId) throws ExecutionException, InterruptedException {
        return productoRepository.get(productoId);
    }

    public List<Producto> getAll() throws ExecutionException, InterruptedException {
        return productoRepository.getAll();
    }

    public void delete(String productoId) throws ExecutionException, InterruptedException {
        productoRepository.delete(productoId);
    }

}
