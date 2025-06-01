import React from 'react';
import GridProductos from './Listado_Productos'; // se utiliza la funcion de listado de productos para llamar los productos
                                                  // de los cuales se utilzaran para mostrarlos en pagina 

function Electricidad() {
  return (
    <div>
      <h2 style={{
        textAlign: "center",
        margin: "32px 0 16px 0",
        fontWeight: "bold",
        letterSpacing: ".8px"
      }}>
        Productos en venta
      </h2> 
      <GridProductos />  
    </div>
  );
}
export default Electricidad;
