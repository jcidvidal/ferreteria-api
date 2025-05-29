import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Inicio from './Inicio/Inicio.js';
import Clientes from './Clientes/Clientes.js';
import Ventas from './Ventas/Ventas.js';
import Stock from './Stock/Stock.js';
import ReclamosSugerencias from './ReclamosSugerencias/ReclamosSugerencias.js';

function App() {
  return (
    <Router>
      <nav style={{ background: '#f5f5f5', padding: 10, marginBottom: 20 }}>
        <Link to="/" style={{ margin: 8 }}>Inicio</Link>
        <Link to="/Clientes" style={{ margin: 8 }}>Clientes</Link>
        <Link to="/ventas" style={{ margin: 8 }}>Ventas</Link>
        <Link to="/stock" style={{ margin: 8 }}>Stock</Link>
        <Link to="/reclamos" style={{ margin: 8 }}>Reclamos y Sugerencias</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/Clientes/Clientes.js" element={<Clientes />} />
        <Route path="/Ventas/Ventas.js" element={<Ventas />} />
        <Route path="/Stock/Stock.js" element={<Stock />} />
        <Route path="/Reclamos/Reclamos.js" element={<ReclamosSugerencias />} />
      </Routes>
    </Router>
  );
}

export default App;
