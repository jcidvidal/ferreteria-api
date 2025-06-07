import React, { useState } from 'react';
import { getAuth, signInWithEmailAndPassword, GoogleAuthProvider, signInWithPopup } from 'firebase/auth';
import { useNavigate } from 'react-router-dom';
import './Login-admin.css';
import app from '../firebase.js';

const auth = getAuth(app);
const provider = new GoogleAuthProvider();

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await signInWithEmailAndPassword(auth, email, password);
      navigate("/inicio");
    } catch (error) {
      alert('Error al iniciar sesión: ' + error.message);
    }
  };

  const handleGoogleLogin = async () => {
    try {
      await signInWithPopup(auth, provider);
      navigate("/inicio");
    } catch (error) {
      alert('Error al iniciar sesión con Google: ' + error.message);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>FERRETERÍA PFEIFER</h2>

        <button type="button" className="google-btn" onClick={handleGoogleLogin}>
          Iniciar sesión con Google
        </button>

        <input
          type="email"
          placeholder="Correo electrónico"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" className="login-btn">Iniciar sesión</button>

        <p>
          ¿No tienes una cuenta? <a href="#">Contacta al administrador</a>
        </p>
      </form>
    </div>
  );
};

export default Login;
