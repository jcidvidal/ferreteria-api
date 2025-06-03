import React from "react";
import "./Clientes.css";
import { Link } from "react-router-dom";

const Clientes = () => {
  return (
    <div className="clientes-container">
      <nav className="navbar">
      <Link to="/" ><div className="logo">Ferretería</div> </Link>  
        <div className="menu">
        <Link to="/Stock"><button>Stock</button> </Link>  
        <Link to="/Clientes"><button className="active">Clientes</button> </Link>  
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
            <tr>
              <td>Juan Pérez</td>
              <td>juanperez@gmail.com</td>
              <td>+56 9 1234 5678</td>
              <td>
                <button className="btn-detalles">Detalles</button>
                <button className="btn-editar">Editar</button>
              </td>
            </tr>
            <tr>
              <td>María González</td>
              <td>mariagonzalez@gmail.com</td>
              <td>+56 9 8765 4321</td>
              <td>
                <button className="btn-detalles">Detalles</button>
                <button className="btn-editar">Editar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Clientes;



