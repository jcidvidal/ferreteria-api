package com.ferreteria.ferreteria_app.model;
    
    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    
    
   		 //Esta clase representa la tabla producto de la base de datos y ademas que es una ent
        //	idad de JPA
   		@Entity
   			 public class Producto {
  
  		     //Estos son los atributos de la tabla producto
  		     @Id
  		     @GeneratedValue(strategy = GenerationType.IDENTITY)
  		     private String id;
  		 
  		     @NotBlank(message = "El nombre es obligatorio")
  	 	    private String nombre;
  	 
  		     private String descripcion;
   
  		     @NotNull(message = "El precio es obligatorio")
  		     @Min(value = 0, message = "El precio no puede ser negativo")
  		     private Double precio;
  		 
  		 
  		     @NotNull(message = "El stock es obligatorio")
 		     @Min(value = 0, message = "El stock no puede ser negativo")
  		     private Integer stock;
  	 
  		     @NotBlank(message = "La categoria es obligatoria")
  		     private String categoria;
   
   
  	 
  		     //Este es el contructor de la clase
  		     // Este se encuentra vacio ya que el framework Jakarta Persistence API(JPA)
  		     // necesita un constructor vacio para crear instacias de recuperacion de datos
  		     public Producto() {
  		 
  		     }
  		 
  		     public String getId() {
  	         return id;
  		     }
  		     public void setId(String id) {
  	         this.id = id;
  		     }
   
  		     public String getNombre() {
  		         return nombre;
  		     }
  		     public void setNombre(String nombre) {
  		         this.nombre = nombre;
  		     }
  		     public String getDescripcion() {
  		         return descripcion;
  		     }
  		     public void setDescripcion(String descripcion) {
  		         this.descripcion = descripcion;
  		     }
  		     public Double getPrecio() {
  		         return precio;
  		     }
  		     public void setPrecio(Double precio) {
  		         this.precio = precio;
  		     }
  		     public Integer getStock() {
  		         return stock;
  		     }
  		 
  	 	    public void setStock(Integer stock) {
  		         this.stock = stock;
  		     }
  		 
  		     public String getCategoria() {
  	         return categoria;
  		     }
  		     public void setCategoria(String categoria) {
  		         this.categoria = categoria;
  		     }
  	
  			 }
