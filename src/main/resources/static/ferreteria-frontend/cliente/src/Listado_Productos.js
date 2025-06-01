import React, { useEffect, useState, useContext } from 'react';
import { collection, getDocs } from "firebase/firestore";
import { db } from './firebaseConfig';
import { CartContext } from './CartContext';
import './GridProductos.css';

function Listado_Productos() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { agregarProducto } = useContext(CartContext);

  useEffect(() => {
    getDocs(collection(db, "productos"))
      .then((querySnapshot) => {
        const lista = [];
        querySnapshot.forEach((doc) => {
          lista.push({ id: doc.id, ...doc.data() });
        });
        setProductos(lista);
        setLoading(false);
      })
      .catch((err) => {
        setError("Error al consultar los productos en Firebase");
        setLoading(false);
      });
  }, []);

  if (loading) return <div style={{textAlign: "center", margin: 40}}>Cargando productos...</div>;
  if (error) return <div style={{color: "red", textAlign: "center", margin: 40}}>{error}</div>;

  return (
    <div className="productos-grid">
      {productos.length === 0 ? (
        <div style={{ gridColumn: '1 / -1', textAlign: 'center' }}>No hay productos disponibles.</div>
      ) : (
        productos.map(prod => (
          <div className="producto-card" key={prod.id}>
            <img src={prod.imagen} alt={prod.nombre} className="producto-img" />
            <h3>{prod.nombre}</h3>
            <p className="desc">{prod.descripcion}</p>
            <div className="info-precio">
              <span className="precio">${prod.precio?.toLocaleString() ?? "—"}</span>
              <span className="stock">Stock: {prod.stock}</span>
            </div>
            <button className="btn-comprar" onClick={() => agregarProducto(prod)}>
              Agregar al carrito
            </button>
          </div>
        ))
      )}
    </div>
  );
}

export default Listado_Productos;

