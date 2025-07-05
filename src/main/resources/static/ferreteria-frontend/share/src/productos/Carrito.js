import React, { useContext } from 'react';
import { CartContext } from '../seguridad/CartContext';


function Carrito() {
  const { cart, eliminarProducto, vaciarCarrito } = useContext(CartContext);

  return (
    <div>
      <h2> Carrito de compras</h2>
      {cart.length === 0 && <p>Tu carrito está vacío.</p>}
      {cart.map((producto) => (
        <div key={producto.id}>
          <p>{producto.nombre} - ${producto.precio}</p>
          <button onClick={() => eliminarProducto(producto.id)}>Eliminar</button>
        </div>
      ))}
      {cart.length > 0 && (
        <>
          <button onClick={vaciarCarrito}>Vaciar carrito</button>
          <button>Finalizar compra</button>
        </>
      )}
    </div>
  );
}

export default Carrito;
