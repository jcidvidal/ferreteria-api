import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Inicio from './inicio/Inicio.js';
import Clientes from './clientes/Clientes.js';
import Ventas from './ventas/Ventas.js';
import Stock from './stock/Stock.js';
import ReclamosSugerencias from './reclamos/Reclamos.js';

function App() {
  return (
    <Router>
      <nav style={{ background: '#f5f5f5', padding: 10, marginBottom: 20 }}>
        <Link to="/" style={{ margin: 8 }}>Inicio</Link>
        <Link to="/clientes" style={{ margin: 8 }}>Clientes</Link>
        <Link to="/ventas" style={{ margin: 8 }}>Ventas</Link>
        <Link to="/stock" style={{ margin: 8 }}>Stock</Link>
        <Link to="/reclamos" style={{ margin: 8 }}>Reclamos y Sugerencias</Link>
      </nav>
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
