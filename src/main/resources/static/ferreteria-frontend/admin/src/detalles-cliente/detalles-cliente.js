import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router‑dom";
import axios from "axios";
import "./detalles‑cliente.css";

const DetallesCliente = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [cliente, setCliente]   = useState(null);
  const [cargando, setCarga]    = useState(true);
  const [guardando, setGuarda]  = useState(false);
  const [error, setError]       = useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/clientes/${id}`)
      .then(r => { setCliente(r.data); setCarga(false); })
      .catch(() => { setError("No se pudo cargar el cliente."); setCarga(false); });
  }, [id]);

  const handleChange = e =>
    setCliente({ ...cliente, [e.target.name]: e.target.value });

  const handleSave = () => {
    setGuarda(true);
    axios.put(`http://localhost:8080/api/clientes/${id}`, cliente)
      .then(() => navigate("/clientes"))
      .catch(() => { setError("No se pudo guardar."); setGuarda(false); });
  };

  if (cargando)      return <p>Cargando…</p>;
  if (!cliente)      return <p>Cliente no encontrado.</p>;

  return (
    <div className="detalle‑container">
      <h2>Detalle de cliente</h2>
      {error && <p className="error">{error}</p>}

      <label>Nombre
        <input name="nombre" value={cliente.nombre || ""} onChange={handleChange} />
      </label>

      <label>Correo
        <input name="correo" value={cliente.correo || ""} onChange={handleChange} />
      </label>

      <label>Teléfono
        <input name="telefono" value={cliente.telefono || ""} onChange={handleChange} />
      </label>

      <div className="acciones">
        <button onClick={handleSave} disabled={guardando}>Guardar</button>
        <button onClick={() => navigate("/clientes")}>Volver</button>
      </div>
    </div>
  );
};

export default DetallesCliente;
