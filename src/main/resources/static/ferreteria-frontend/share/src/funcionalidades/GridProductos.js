import React, { useEffect, useState } from "react";
import axios from "axios";

function GridProductos() {
  const [productos, setProductos] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/api/productos")
      .then((res) => setProductos(res.data))
      .catch((err) => {
        console.error("[ERROR] al obtener productos:", err);
        setError("Error al cargar productos.");
      });
  }, []);

  if (error) return <div style={{ color: "red" }}>{error}</div>;

  return (
    <div>
      <h2>Inventario de Productos</h2>
      {productos.length === 0 ? (
        <p>No hay productos registrados.</p>
      ) : (
        <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th>Producto</th>
              <th>Categoría</th>
              <th>Cantidad</th>
              <th>Precio</th>
            </tr>
          </thead>
          <tbody>
            {productos.map((producto) => (
              <tr key={producto.idProducto}>
                <td>{producto.nombre}</td>
                <td>{producto.categoria}</td>
                <td>{producto.cantidad}</td>
                <td>${producto.precio.toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default GridProductos;
