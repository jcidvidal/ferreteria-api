package com.ferreteria.ferreteria_app.service;


import com.ferreteria.ferreteria_app.model.Producto;
import com.ferreteria.ferreteria_app.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


//Este servicio representa la capa de negocio de producto
@Service
public class productoService {

    private ProductoRepository ProductoRepository;

    @Autowired
    public void ProductoService (ProductoRepository productoRepository){
        this.ProductoRepository = productoRepository;
    };

    //este metodo permite guardar un producto en la base de datos
    public Producto guardarProducto(Producto producto){
        return ProductoRepository.save(producto);
    }

    //este metodo permite obtener todos los productos de la base de datos
    //mediante un arraylist
    public List<Producto> listarProductos(){
        return ProductoRepository.findAll();
    }

    //este metodo permite obtener un producto por su id
    public Producto obtenerProductoPorId(String id){
	 return ProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id>
    }

    //este metodo permite eliminar un producto por su id
    public void eliminarProducto(String id){
        ProductoRepository.deleteById(id);
    }
    //este nos permite actualizar un producto ya existente
    //utitilizando todos los atributos del producto
    public void actualizarProducto(Producto producto){
        producto = obtenerProductoPorId(id);
        producto.setNombre(producto.getNombre());
        producto.setDescripcion(producto.getDescripcion());
        producto.setPrecio(producto.getPrecio());
        producto.setStock(producto.getStock());
        producto.setCategoria(producto.getCategoria());
        ProductoRepository.save(producto);
    }
}
