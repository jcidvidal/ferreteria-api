import React, { useEffect, useState } from "react";
import { getFirestore, collection, query, where, getDocs } from "firebase/firestore";
import { app } from "../firebaseConfig";

const db = getFirestore(app);

const GridProductos = ({ categoria }) => {
    const [productos, setProductos] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProductos = async () => {
            setLoading(true);
            const productosRef = collection(db, "Productos");
            const q = query(productosRef, where("categoria", "==", "seguridad"));
            const querySnapshot = await getDocs(q);
            const productosArr = querySnapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
            console.log("[DEBUG] Docs traídos de Firestore:", productosArr);
            setProductos(productosArr);
            setLoading(false);
        };

        fetchProductos();
    }, [categoria]);

    if (loading) return <div>Cargando productos...</div>;
    if (productos.length === 0) return <div>No hay productos en esta categoría.</div>;

    return (
        <div className="lista-productos">
            {productos.map(producto => (
                <div className="tarjeta-producto" key={producto.id}>
                    <h3>{producto.nombre}</h3>
                    <p><b>Precio:</b> ${producto.precio}</p>
                    <p><b>Stock:</b> {producto.stock}</p>
                </div>
            ))}
        </div>
    );
};

export default GridProductos;
