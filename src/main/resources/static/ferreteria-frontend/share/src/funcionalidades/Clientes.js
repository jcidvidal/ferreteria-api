import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css-admin/Clientes.css";
import { Link } from "react-router-dom";
import GridClientes from "./GridClientes";

const Clientes = () => {
  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/clientes")
      .then((res) => setClientes(res.data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div className="clientes-container">
      <nav className="navbar">
        <Link to="/"><div className="logo">Ferretería</div></Link>
        <div className="menu">
          <Link to="/Stock"><button>Stock</button></Link>
          <Link to="/Clientes"><button className="active">Clientes</button></Link>
          <Link to="/Reclamos"><button>Reclamos/Sugerencias</button></Link>
          <Link to="/Ventas"><button>Ventas</button></Link>
        </div>
      </nav>

      <div className="clientes-content">
        <h2>Listado de Clientes</h2>

        {clientes.length === 0 ? (
          <p>No hay clientes registrados.</p>
        ) : (
          <table className="clientes-table">
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Teléfono</th>
              </tr>
            </thead>
            <tbody>
              {clientes.map((cliente) => (
                <tr key={cliente.idCliente}>
                  <td>{cliente.nombre}</td>
                  <td>{cliente.email}</td>
                  <td>{cliente.telefono}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default Clientes;



