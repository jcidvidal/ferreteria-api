import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Inicio from './funcionalidades/Inicio.js';
import Clientes from './funcionalidades/Clientes.js';
import Ventas from './funcionalidades/Ventas.js';
import Stock from './funcionalidades/Stock.js';
import ReclamosSugerencias from './funcionalidades/Reclamos.js';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/clientes/:id"    element={<DetallesCliente />} />
        <Route path="/ventas" element={<Ventas />} />
        <Route path="/stock" element={<Stock />} />
        <Route path="/stock/:id"       element={<DetallesStock />} />
        <Route path="/reclamos" element={<ReclamosSugerencias />} />
      </Routes>
    </Router>
  );
}

export default App;
