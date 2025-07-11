import React, { useEffect, useState } from "react";
import axios from "axios";

function GridClientes() {
  const [clientes, setClientes] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/api/clientes")
      .then((res) => setClientes(res.data))
      .catch((err) => {
        console.error("[ERROR] al obtener clientes:", err);
        setError("Error al cargar clientes.");
      });
  }, []);

  if (error) return <div style={{ color: "red" }}>{error}</div>;

  return (
    <div>
      <h2>Listado de Clientes</h2>
      {clientes.length === 0 ? (
        <p>No hay clientes registrados.</p>
      ) : (
        <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
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
  );
}

export default GridClientes;
