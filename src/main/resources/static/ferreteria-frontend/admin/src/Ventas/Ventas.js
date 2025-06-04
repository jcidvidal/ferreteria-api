import React from "react";
import "./Ventas.css";
import { Link } from "react-router-dom";


const Ventas = () => {
  return (
    <div className="ventas-container">
      <nav className="navbar">
      <Link to ="/"><div className="logo">Ferretería</div></Link>  
        <div className="menu">
        <Link to ="/Stock">  <button>Stock</button></Link> 
         <Link to="/Clientes" style={{ margin: 8 }}><button>Clientes</button></Link> 
         <Link to="/Reclamos" style={{ margin: 8 }}><button>Reclamos y Sugerencias</button></Link> 
          <Link to="/Ventas" style={{ margin: 8 }}><button className="active">Ventas</button></Link>
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


