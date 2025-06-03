import React from "react";
import "./Reclamos.css";
import { Link } from "react-router-dom";

const ReclamosSugerencias = () => {
  return (
    <div className="reclamos-container">
      <nav className="navbar">
      <Link to="/"><div className="logo">Ferretería</div></Link>  
        <div className="menu">
        <Link to="/Stock" > <button>Stock</button>  </Link>  
        <Link to="/Clientes" > <button>Clientes</button> </Link>  
        <Link to="/Reclamos" > <button className="active">ReclamosSugerencias</button> </Link>  
        <Link to="/Ventas"><button>Ventas</button> </Link>  
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


