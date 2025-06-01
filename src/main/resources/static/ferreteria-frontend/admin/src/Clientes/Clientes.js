import React from "react";
import "./Clientes.css";

const Clientes = () => {
  return (
    <div className="clientes-container">
      <nav className="navbar">
        <div className="logo">Ferretería</div>
        <div className="menu">
          <button>Stock</button>
          <button className="active">Clientes</button>
          <button>Reclamos/Sugerencias</button>
          <button>Ventas</button>
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



