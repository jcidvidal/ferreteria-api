package com.ferreteria.ferreteria_app.repository;
   	 
   		 import com.ferreteria.ferreteria_app.model.Producto;
   		 import org.springframework.data.jpa.repository.JpaRepository;
   		 import org.springframework.stereotype.Repository;
   		 import java.util.List;
    
    
   		 //Esta clase representa el repositorio de la tabla producto de la base de datos
  		 //permite que spring detecte automaticamente este repositorio durante el escaneo de l
     	 as clases
  		 //---------------------------------
  		 // al extenderlo nos permite usar funcionalidades heredadas en las demas clases actua
      	// ndo como clase padre
  		 //dejandonos utilizar save(), findById(), findAll(), deleteById()
  		 @Repository
  		 public interface ProductoRepository extends JpaRepository<Producto, Long> {
   
  
