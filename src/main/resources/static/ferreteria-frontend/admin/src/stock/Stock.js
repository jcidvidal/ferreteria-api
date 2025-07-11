import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Stock.css";
import { Link } from "react-router-dom";

const Stock = () => {
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/productos")
      .then((res) => setProductos(res.data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div className="stock-container">
      <nav className="navbar">
        <Link to="/"><div className="logo">Ferretería</div></Link>
        <div className="menu">
          <Link to="/Stock"><button className="active">Stock</button></Link>
          <Link to="/Clientes"><button>Clientes</button></Link>
          <Link to="/Reclamos"><button>Reclamos/Sugerencias</button></Link>
          <Link to="/Ventas"><button>Ventas</button></Link>
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
            {productos.map((producto) => (
              <tr key={producto.id}>
                <td>{producto.nombre}</td>
                <td>{producto.categoria}</td>
                <td>{producto.cantidad}</td>
                <td>{producto.precio}</td>
                <td>
                  <Link to={`/stock/${producto.id}`}>
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

export default Stock;



