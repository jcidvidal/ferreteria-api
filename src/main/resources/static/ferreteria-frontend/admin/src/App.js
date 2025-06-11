import React from 'react';
HEAD
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './Login/Login.js';
origin/front-end
import Inicio from './Inicio/Inicio.js';
import Clientes from './Clientes/Clientes.js';
import Ventas from './Ventas/Ventas.js';
import Stock from './Stock/Stock.js';
import ReclamosSugerencias from './ReclamosSugerencias/Reclamos.js';
HEAD
import Login from './Login/Login.js';

import PrivateRoute from './Validaciones/PrivateRoute.js';
import { AuthProvider, useAuth } from './Validaciones/AuthContext.js';
import { getAuth, signOut } from 'firebase/auth';

function AppContent() {
  const { user } = useAuth();
  const auth = getAuth();

  const handleLogout = () => {
    signOut(auth);
  };
 origin/front-end

  return (
    <Router>
HEAD
      <Routes>
        <Route path="/Inicio" element={<Inicio />} />
        <Route path="/" element={<Login />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/ventas" element={<Ventas />} />
        <Route path="/stock" element={<Stock />} />
        <Route path="/reclamos" element={<ReclamosSugerencias />} />

      <nav style={{
        background: '#f5f5f5',
        padding: 10,
        marginBottom: 20,
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
      }}>
        <div>
          <Link to="/inicio" style={{ margin: 8 }}>Inicio</Link>
          <Link to="/clientes" style={{ margin: 8 }}>Clientes</Link>
          <Link to="/ventas" style={{ margin: 8 }}>Ventas</Link>
          <Link to="/stock" style={{ margin: 8 }}>Stock</Link>
          <Link to="/reclamos" style={{ margin: 8 }}>Reclamos y Sugerencias</Link>
        </div>
        {user && (
          <button
            onClick={handleLogout}
            style={{
              marginLeft: 20,
              backgroundColor: '#ff5252',
              color: 'white',
              border: 'none',
              padding: '5px 10px',
              borderRadius: '5px',
              cursor: 'pointer'
            }}
          >
            Cerrar sesión
          </button>
        )}
      </nav>

      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/inicio" element={<PrivateRoute element={<Inicio />} />} />
        <Route path="/clientes" element={<PrivateRoute element={<Clientes />} />} />
        <Route path="/ventas" element={<PrivateRoute element={<Ventas />} />} />
        <Route path="/stock" element={<PrivateRoute element={<Stock />} />} />
        <Route path="/reclamos" element={<PrivateRoute element={<ReclamosSugerencias />} />} />
        <Route path="*" element={<Login />} />
 origin/front-end
      </Routes>
    </Router>
  );
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
