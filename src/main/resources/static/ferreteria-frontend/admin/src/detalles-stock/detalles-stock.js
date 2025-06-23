import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router‑dom";
import axios from "axios";
import "./detalles‑stock.css";

const DetallesStock = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [producto, setProducto] = useState(null);
  const [cargando, setCarga]    = useState(true);
  const [guardando, setGuarda]  = useState(false);
  const [error, setError]       = useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/productos/${id}`)
      .then(r => { setProducto(r.data); setCarga(false); })
      .catch(() => { setError("No se pudo cargar el producto."); setCarga(false); });
  }, [id]);

  const handleChange = e =>
    setProducto({ ...producto, [e.target.name]: e.target.value });

  const handleSave = () => {
    setGuarda(true);
    axios.put(`http://localhost:8080/api/productos/${id}`, producto)
      .then(() => navigate("/stock"))
      .catch(() => { setError("No se pudo guardar."); setGuarda(false); });
  };

  if (cargando)     return <p>Cargando…</p>;
  if (!producto)    return <p>Producto no encontrado.</p>;

  return (
    <div className="detalle‑container">
      <h2>Detalle de producto</h2>
      {error && <p className="error">{error}</p>}

      <label>Nombre
        <input name="nombre" value={producto.nombre || ""} onChange={handleChange} />
      </label>

      <label>Categoría
        <input name="categoria" value={producto.categoria || ""} onChange={handleChange} />
      </label>

      <label>Cantidad
        <input type="number" name="cantidad" value={producto.cantidad || 0} onChange={handleChange} />
      </label>

      <label>Precio
        <input type="number" name="precio" value={producto.precio || 0} onChange={handleChange} />
      </label>

      <div className="acciones">
        <button onClick={handleSave} disabled={guardando}>Guardar</button>
        <button onClick={() => navigate("/stock")}>Volver</button>
      </div>
    </div>
  );
};

export default DetallesStock;
