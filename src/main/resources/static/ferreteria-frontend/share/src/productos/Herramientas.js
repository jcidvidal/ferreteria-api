import React from 'react';
import GridProductos from './Listado_Productos';

function Herramientas() {
  console.log("Componente HERRAMIENTAS cargado");
  return (
    <div>
      <h2>Herramientas en venta</h2>
      <GridProductos categoria="herramientas" />
    </div>
  );
}

export default Herramientas;
