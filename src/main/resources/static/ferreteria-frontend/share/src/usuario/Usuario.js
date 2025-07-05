import React, { useEffect, useState } from 'react';
import '../css/usuario.css';

function Usuario() {
  const [usuario, setUsuario] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const usuarioId = 1; 

  useEffect(() => {
    // Alojamiento de los datos del usuario de forma local
    fetch(`http://localhost:8080/api/usuarios/${usuarioId}`)
      .then((res) => {
        if (!res.ok) throw new Error('No se pudo obtener los datos del usuario');
        return res.json();
      })
      .then((data) => {
        setUsuario(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [usuarioId]);

  if (loading) return <div style={{textAlign: "center", margin: 40}}>Cargando datos de usuario...</div>;
  if (error) return <div style={{color: "red", textAlign: "center", margin: 40}}>{error}</div>;

  return (
    <div className="usuario-container">
      <h2 className="usuario-titulo">Mi Perfil</h2>
      <div className="usuario-card">
        <img
          src={`https://ui-avatars.com/api/?name=${encodeURIComponent(usuario.nombre)}&background=FF9900&color=fff`}
          alt="Avatar"
          className="usuario-avatar"
        />
        <div className="usuario-datos">
          <div><b>Nombre:</b> {usuario.nombre}</div>
          <div><b>Email:</b> {usuario.email}</div>
          <div><b>Dirección:</b> {usuario.direccion}</div>
          <div><b>Teléfono:</b> {usuario.telefono}</div>
          <div><b>RUT:</b> {usuario.rut}</div>
        </div>
      </div>
    </div>
  );
}

export default Usuario;
