import React from 'react';
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Inicio from './Inicio/Inicio.js';
import Clientes from './Clientes/Clientes.js';
import Ventas from './Ventas/Ventas.js';
import Stock from './Stock/Stock.js';
import ReclamosSugerencias from './ReclamosSugerencias/Reclamos.js';
import Login from './Login/Login.js';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/Inicio" element={<Inicio />} />
        <Route path="/" element={<Login />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/ventas" element={<Ventas />} />
        <Route path="/stock" element={<Stock />} />
        <Route path="/reclamos" element={<ReclamosSugerencias />} />
      </Routes>
    </Router>
  );
}

export default App;
