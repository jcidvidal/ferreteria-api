import React, { createContext, useState, useEffect } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  // Cargar carrito al iniciar
  useEffect(() => {
    const cartGuardado = localStorage.getItem('cart');
    if (cartGuardado) {
      setCart(JSON.parse(cartGuardado));
    }
  }, []);

  // Guardar carrito en localStorage al actualizar
  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart));
  }, [cart]);

  const agregarProducto = (producto) => {
    setCart([...cart, producto]);
  };

  const eliminarProducto = (idProducto) => {
    setCart(cart.filter(p => p.id !== idProducto));
  };

  const vaciarCarrito = () => setCart([]);

  return (
    <CartContext.Provider value={{ cart, agregarProducto, eliminarProducto, vaciarCarrito }}>
      {children}
    </CartContext.Provider>
  );
};


