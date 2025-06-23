import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Inicio from './inicio/Inicio.js';
import Clientes from './clientes/Clientes.js';
import Ventas from './ventas/Ventas.js';
import Stock from './stock/Stock.js';
import ReclamosSugerencias from './reclamos/Reclamos.js';
import DetallesStock   from './detalles-stock/detalles-stock';
import DetallesCliente from './detalles-cliente/detalles-cliente';

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
