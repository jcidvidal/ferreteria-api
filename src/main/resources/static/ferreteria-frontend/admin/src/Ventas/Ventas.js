import React from "react";
import "./Ventas.css";

const Ventas = () => {
  return (
    <div className="ventas-container">
      <nav className="navbar">
        <div className="logo">Ferretería</div>
        <div className="menu">
          <button>Stock</button>
          <button>Clientes</button>
          <button>Reclamos/Sugerencias</button>
          <button className="active">Ventas</button>
        </div>
      </nav>

      <div className="ventas-content">
        <h2>Historial de Ventas</h2>
        <table className="ventas-table">
          <thead>
            <tr>
              <th>Fecha</th>
              <th>Cliente</th>
              <th>Producto</th>
              <th>Cantidad</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>2025-05-29</td>
              <td>Juan Pérez</td>
              <td>Martillo</td>
              <td>2</td>
              <td>$10.000</td>
            </tr>
            <tr>
              <td>2025-05-28</td>
              <td>María González</td>
              <td>Taladro</td>
              <td>1</td>
              <td>$45.000</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Ventas;


