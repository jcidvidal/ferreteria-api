import React, { useEffect, useState } from 'react';
import { collection, getDocs } from "firebase/firestore";
import { db } from '../firebase/firebaseConfig';

function GridProductos({ categoria = "herramientas" }) {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("[DEBUG] Categoría recibida en GridProductos:", categoria);
    getDocs(collection(db, categoria))
      .then((querySnapshot) => {
        const lista = [];
        querySnapshot.forEach((doc) => {
          lista.push({ id: doc.id, ...doc.data() });
        });
        console.log("[DEBUG] Productos obtenidos:", lista);
        setProductos(lista);
        setLoading(false);
      })
      .catch((err) => {
        setError("Error al consultar los productos en Firebase");
        setLoading(false);
        console.error("[DEBUG] Error al consultar Firestore:", err);
      });
  }, [categoria]);

  if (loading) return <div>Cargando productos...</div>;
  if (error) return <div style={{color:"red"}}>{error}</div>;

  return (
    <div>
      <h3>Productos Debug</h3>
      <ul>
        {productos.map(p => (
          <li key={p.id}>{p.nombre} — {p.precio}</li>
        ))}
      </ul>
    </div>
  );
}

export default GridProductos;
