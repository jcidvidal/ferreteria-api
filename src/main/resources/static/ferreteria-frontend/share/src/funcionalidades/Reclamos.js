import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css-admin/Reclamos.css";
import { Link } from "react-router-dom";

const ReclamosSugerencias = () => {
  const [reclamos, setReclamos] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/reclamos") // Ajusta la URL si usas otro puerto
      .then((res) => setReclamos(res.data))
      .catch((err) => console.error("Error al cargar reclamos:", err));
  }, []);

  return (
    <div className="reclamos-container">
      <nav className="navbar">
        <Link to="/"><div className="logo">Ferretería</div></Link>
        <div className="menu">
          <Link to="/Stock"><button>Stock</button></Link>
          <Link to="/Clientes"><button>Clientes</button></Link>
          <Link to="/Reclamos"><button className="active">Reclamos/Sugerencias</button></Link>
          <Link to="/Ventas"><button>Ventas</button></Link>
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
            {reclamos.map((item, index) => (
              <tr key={index}>
                <td>{item.cliente}</td>
                <td>{item.tipo}</td>
                <td>{item.mensaje}</td>
                <td>{item.fecha}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ReclamosSugerencias;


