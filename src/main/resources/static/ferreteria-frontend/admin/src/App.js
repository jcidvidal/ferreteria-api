import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Inicio from './inicio/Inicio.js';
import Clientes from './clientes/Clientes.js';
import Ventas from './ventas/Ventas.js';
import Stock from './stock/Stock.js';
import ReclamosSugerencias from './reclamos/Reclamos.js';

function App() {
  return (
    <Router>
    
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/ventas" element={<Ventas />} />
        <Route path="/stock" element={<Stock />} />
        <Route path="/reclamos" element={<ReclamosSugerencias />} />
      </Routes>
    </Router>
  );
}

export default App;
