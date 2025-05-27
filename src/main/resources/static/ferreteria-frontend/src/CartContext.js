import React, { createContext, useState, useEffect } from 'react';
import CryptoJS from 'crypto-js';
export const CartContext = createContext(); // Usaremos este nombre en toda la app

const SECRET_KEY = 'MARCO1313$';

const cifrarDatos = (datos) => {
  return CryptoJS.AES.encrypt(JSON.stringify(datos), SECRET_KEY).toString();
};

const descifrarDatos = (datosCifrados) => {
  try {
    const bytes = CryptoJS.AES.decrypt(datosCifrados, SECRET_KEY);
    return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
  } catch (e) {
    console.error('Error al descifrar:', e);
    return [];
  }
};

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  // Cargar carrito cifrado al iniciar
  useEffect(() => {
    const cartCifrado = localStorage.getItem('cartCifrado');
    if (cartCifrado) {
      setCart(descifrarDatos(cartCifrado));
    }
  }, []);

  // Guardar carrito cifrado al actualizar el carrito
  useEffect(() => {
    localStorage.setItem('cartCifrado', cifrarDatos(cart));
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
