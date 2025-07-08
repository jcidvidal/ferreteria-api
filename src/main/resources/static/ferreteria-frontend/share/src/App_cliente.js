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
        <Route path="/productos/*" element={<Productos />} />
        <Route path="/materiales/*" element={<Materiales />} />
        <Route path="/seguridad/*" element={<Seguridad />} />
        <Route path="/plomeria/*" element={<Plomeria />} />
        <Route path="/electricidad/*" element={<Electricidad />} />
        <Route path="/jardin/*" element={<Jardin />} />
        <Route path="/herramientas/*" element={<Herramientas />} />
   

        <Route path="/contacto/*" element={<Contacto />} />
        <Route path="/carrito/*" element={<Carrito />} />
        <Route path="/usuario/*" element={<Usuario />} />
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
