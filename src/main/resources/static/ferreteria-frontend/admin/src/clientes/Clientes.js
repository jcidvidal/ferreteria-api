import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Clientes.css";
import { Link } from "react-router-dom";

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
        <table className="clientes-table">
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Correo</th>
              <th>Teléfono</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map((cliente) => (
              <tr key={cliente.id}>
                <td>{cliente.nombre}</td>
                <td>{cliente.correo}</td>
                <td>{cliente.telefono}</td>
                <td>
                  <Link to={`/clientes/${cliente.id}`}>
                    <button>Detalles</button>
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Clientes;




