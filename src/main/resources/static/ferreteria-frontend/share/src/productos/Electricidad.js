import React from 'react';
import GridProductos from './Listado_Productos'; // o '../cliente/Listado_Productos', ajusta si es necesario

function Electricidad() {
  return (
    <div>
      <h2 style={{
        textAlign: "center",
        margin: "32px 0 16px 0",
        fontWeight: "bold",
        letterSpacing: ".8px"
      }}>
        Productos de Electricidad
      </h2>
      <GridProductos categoria="electricidad" />
    </div>
  );
}

export default Electricidad;
