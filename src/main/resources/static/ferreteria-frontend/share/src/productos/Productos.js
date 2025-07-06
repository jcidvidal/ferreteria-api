import React from 'react';
import GridProductos from './Listado_Productos';

function Productos() {
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

export default Productos;