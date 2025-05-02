package com.ferreteria.ferreteria_app.controller;

import com.ferreteria.ferreteria_app.model.Producto;
import com.ferreteria.ferreteria_app.service.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import  org.springframework.web.bind.annotation.*;
import java.util.List;

//Esta clase representa el controlador de la API REST de producto
//la ruta de la API es /api/productos
@RestController
@RequestMapping("/api/productos")
public class productoController {

    private productoService productoService;

    // Este constructor se encarga de inyectar la dependencia productoService
    @Autowired
    public void productoController(productoService productoService){
        this.productoService = productoService;
    }

// este endpoint permite obtener todos los productos listandolos
// ya que esta tiene una funcion que devuelve una lista de productos
    @GetMapping
    public List<Producto> listarProductos(){
        return productoService.listarProductos();
    }

//Este endpoint permite crear un producto y guardarlo en la base de datos
//usando como ruta /api/productos
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto){
return productoService.guardarProducto(producto);
    }

    //Este endpoint permite obtener un producto por su id
    //usando como ruta /api/productos/{id}
    //filtrando mediante el id que se pasa como parametro
    //de esta forma {id}
    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable String id){
        return productoService.obtenerProductoPorId(id);
    }

    //Este endpoint permite actualizar un producto ya existente
    //usando la ruta predeterminada de los anteriores endpoint
    //filtrando mediante el id que se pasa como parametro, ya que este es el id de la tabla p>
    @PutMapping ("/{id}")
    public void actualizarProducto(@PathVariable String id, @RequestBody Producto producto){
        productoService.actualizarProducto(producto);
    }

    //Este endpoint permite eliminar un producto ya existente
    //filtrando mediante el id que se pasa como parametro, ya que este es el id de la tabla p>
    @DeleteMapping ("/{id}")
    public void eliminarProducto(@PathVariable String id){
        productoService.eliminarProducto(id);
    }

    //este endpoint permite actualizar el stock de un producto ya existente
    //utilizando tres parametros: id, stock y cantidad
    //y ademas calcula el nuevo stock del producto
  @PatchMapping("/{id}/{stock}/{cantidad}")
    public Producto actualizarStock(@PathVariable String id, @PathVariable Integer stock, @Pa>
        Producto producto = productoService.obtenerProductoPorId(id);
        producto.setStock(stock + cantidad);
        productoService.actualizarProducto(producto);
        return producto;
    }

}
