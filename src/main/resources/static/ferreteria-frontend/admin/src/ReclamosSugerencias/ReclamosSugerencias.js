import React from "react";
import "./ReclamosSugerencias.css";

const ReclamosSugerencias = () => {
  return (
    <div className="reclamos-container">
      <nav className="navbar">
        <div className="logo">Ferretería</div>
        <div className="menu">
          <button>Stock</button>
          <button>Clientes</button>
          <button className="active">Reclamos/Sugerencias</button>
          <button>Ventas</button>
        </div>
      </nav>

      <div className="reclamos-content">
        <h2>Reclamos y Sugerencias</h2>
        <table className="reclamos-table">
          <thead>
            <tr>
              <th>Cliente</th>
              <th>Tipo</th>
              <th>Mensaje</th>
              <th>Fecha</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Ana López</td>
              <td>Reclamo</td>
              <td>Producto defectuoso</td>
              <td>2025-05-30</td>
            </tr>
            <tr>
              <td>Pedro Rojas</td>
              <td>Sugerencia</td>
              <td>Agregar más métodos de pago</td>
              <td>2025-05-28</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ReclamosSugerencias;


