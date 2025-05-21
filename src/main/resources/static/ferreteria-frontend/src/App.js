import React from 'react';
import { Routes, Route } from 'react-router-dom';
import NavbarCliente from './NavbarCliente';
import Productos from './Productos';
import Materiales from './Materiales';
import Seguridad from './Seguridad';
import Plomeria from './Plomeria';
import Electricidad from './Electricidad';
import Jardin from './Jardin';
import Herramientas from './Herramientas';
import Contacto from './Contacto';



function App() {
  return (
    <>
      <NavbarCliente />
      <Routes>
        <Route path="/productos" element={<Productos />} />
        <Route path="/materiales" element={<Materiales />} />
        <Route path="/seguridad" element={<Seguridad />} />
        <Route path="/plomeria" element={<Plomeria />} />
        <Route path="/Electricidad" element={<Electricidad />} />
        <Route path="/Jardin" element={<Jardin />} />
        <Route path="/Herramientas" element={<Herramientas/>} />
        <Route path="/Contacto" element={<Contacto/>} />


        {/* Ruta principal */}
        <Route path="/" element={<h2 style={{textAlign: 'center', marginTop: 40}}>Bienvenido a la Ferretería</h2>} />
      </Routes>
    </>
  );
}

export default App;

