import React from 'react';
import GridProductos from './Listado_Productos'; // se utiliza la funcion de listado de productos para llamar los productos
// de los cuales se utilzaran para mostrarlos en pagina

function Jardin() {
  return (
    <div>
      <h2 style={{
        textAlign: "center",
        margin: "32px 0 16px 0",
        fontWeight: "bold",
        letterSpacing: ".8px"
      }}>
        Productos en venta
      <GridProductos categoria="Jardin" />
      </h2>
    </div>
  );
}

export default Jardin;