import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Productos from './productos/Productos';
import Materiales from './productos/Materiales';
import Seguridad from './seguridad/Seguridad';
import Plomeria from './productos/Plomeria';
import Electricidad from './productos/Electricidad';
import Jardin from './productos/Jardin';
import Herramientas from './productos/Herramientas';
import Contacto from './usuario/Contacto';
import Carrito from './productos/Carrito';
import Usuario from './usuario/Usuario';
import NavbarCliente from "./NavbarCliente";
import { CartProvider } from './seguridad/CartContext';

function App() {
  return (
    <CartProvider>
      <NavbarCliente />
      <Routes>
        {/* Todas las rutas bajo /cliente/productos/xxx */}
        <Route path="cliente/productos/productos" element={<Productos />} />
        <Route path="cliente/productos/materiales" element={<Materiales />} />
        <Route path="cliente/seguridad/seguridad" element={<Seguridad />} />
        <Route path="cliente/productos/plomeria" element={<Plomeria />} />
        <Route path="cliente/productos/electricidad" element={<Electricidad />} />
        <Route path="cliente/productos/jardin" element={<Jardin />} />
        <Route path="cliente/productos/herramientas" element={<Herramientas />} />
   

        <Route path="cliente/usuario/contacto" element={<Contacto />} />
        <Route path="cliente/productos/carrito" element={<Carrito />} />
        <Route path="cliente/usuario/usuario" element={<Usuario />} />
        {/* Ruta principal */}
        <Route path="/" element={
          <h2 style={{ textAlign: 'center', marginTop: 40 }}>
            Bienvenido a la Ferretería
          </h2>
        } />
      </Routes>
    </CartProvider>
  );
}

export default App;
