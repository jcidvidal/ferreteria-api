import React, { useEffect, useState } from "react";
    import axios from "axios";
    import "../css-admin/Ventas.css";
    import { Link } from "react-router-dom";

    const Ventas = () => {
      const [ventas, setVentas] = useState([]);

      useEffect(() => {
        axios.get("http://localhost:8080/api/ventas")
          .then((res) => setVentas(res.data))
          .catch((err) => console.error("Error al cargar ventas:", err));
      }, []);

      return (
        <div className="ventas-container">
          <nav className="navbar">
            <Link to="/"><div className="logo">Ferretería</div></Link>
            <div className="menu">
              <Link to="/Stock"><button>Stock</button></Link>
              <Link to="/Clientes"><button>Clientes</button></Link>
              <Link to="/Reclamos"><button>Reclamos y Sugerencias</button></Link>
              <Link to="/Ventas"><button className="active">Ventas</button></Link>
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
                {ventas.map((venta) => (
                  <tr key={venta.id}>
                    <td>{venta.fecha}</td>
                    <td>{venta.cliente}</td>
                    <td>{venta.producto}</td>
                    <td>{venta.cantidad}</td>
                    <td>${venta.total.toLocaleString("es-CL")}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      );
    };

    export default Ventas;

