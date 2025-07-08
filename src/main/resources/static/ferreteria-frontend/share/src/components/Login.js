import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "../css/Login_style.css";

//  Mapeo de rutas por el rol 
export const MAPEO_ROLES = {
  CLIENTE: {
    rutaReact: "/cliente",
  },
  ADMIN: {
    rutaReact: "/admin",
  }
};

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, contrasena);
      const token = await userCredential.user.getIdToken();

      // Llama al backend con Authorization
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );

      localStorage.setItem("jwtToken", response.data.token);

      const rol = response.data.rol?.toUpperCase();
      if (rol && MAPEO_ROLES[rol]) {
        navigate(MAPEO_ROLES[rol].rutaReact);
      } else {
        setError("No se reconoce el tipo de usuario. Contacte a soporte.");
      }

    } catch (err) {
      let mensaje = "";
      if (err.response) {
        if (err.response.data) {
          mensaje = typeof err.response.data === "string"
            ? err.response.data
            : (err.response.data.message || JSON.stringify(err.response.data));
        } else {
          mensaje = "Respuesta vacía del servidor";
        }
      } else {
        mensaje = err.message;
      }
      setError(mensaje);
    }
  };

  return (
    <div className="login-unix-bg">
      <form onSubmit={handleLogin} className="login-unix-container">
        <h2 className="login-unix-title">FERRETERIA PFEIFER</h2>
        {error && <p style={{ color: "red", marginBottom: "8px" }}>{error}</p>}
        <input
          className="login-unix-input"
          type="email"
          placeholder="Email address..."
          required
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="login-unix-input"
          type="password"
          placeholder="Password..."
          required
          value={contrasena}
          onChange={(e) => setContrasena(e.target.value)}
        />
        <button className="login-unix-btn" type="submit">Iniciar sesión</button>
        <div className="login-unix-small">
          ¿No tienes cuenta?
          <Link to="/register">Regístrate</Link>
        </div>
      </form>
    </div>
  );
};

export default Login;
