import React, { useEffect, useState } from 'react';
import { collection, getDocs } from "firebase/firestore";
import { db } from '../firebase/firebaseConfig';

function GridProductos({ categoria = "herramientas" }) {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("[DEBUG] Categoría recibida en GridProductos:", categoria); //mensaje para mostrar que esta funcionando el listado mediante el dom
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
      .catch((err) => {   //envuelto en una excepcion para que muestre un mensaje de error 
        setError("Error al consultar los productos en Firebase");  // Mensaje que se va a apreciar en pantall
        setLoading(false);
        console.error("[DEBUG] Error al consultar Firestore:", err); //
      });
  }, [categoria]);

  if (loading) return <div>Cargando productos...</div>;
  if (error) return <div style={{color:"red"}}>{error}</div>;

  return (
    <div>
      <h3>Productos</h3>
      <ul>
        {productos.map(p => (
          <li key={p.id}>{p.nombre} — {p.precio}</li>
        ))}
      </ul>
    </div>
  );
}

export default GridProductos;
