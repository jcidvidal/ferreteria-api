import React from "react";
import "./Stock.css";

const Stock = () => {
  return (
    <div className="stock-container">
      <nav className="navbar">
        <div className="logo">Ferretería</div>
        <div className="menu">
          <button className="active">Stock</button>
          <button>Clientes</button>
          <button>Reclamos/Sugerencias</button>
          <button>Ventas</button>
        </div>
      </nav>

      <div className="stock-content">
        <h2>Inventario de Productos</h2>
        <table className="stock-table">
          <thead>
            <tr>
              <th>Producto</th>
              <th>Categoría</th>
              <th>Cantidad</th>
              <th>Precio Unitario</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Martillo</td>
              <td>Herramientas</td>
              <td>25</td>
              <td>$5.000</td>
              <td>
                <button className="btn-editar">Editar</button>
              </td>
            </tr>
            <tr>
              <td>Taladro</td>
              <td>Eléctricas</td>
              <td>10</td>
              <td>$45.000</td>
              <td>
                <button className="btn-editar">Editar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Stock;


