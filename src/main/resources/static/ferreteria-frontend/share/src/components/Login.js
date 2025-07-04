import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      // 1. Inicia sesión en Firebase y obtiene el token
      const userCredential = await signInWithEmailAndPassword(auth, email, contrasena);
      const token = await userCredential.user.getIdToken();

      // Debug: Muestra el token JWT de Firebase
      console.log("Token JWT de Firebase:", token);

      // 2. Llama al backend SOLO con el header Authorization, body vacío
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {}, // Body vacío, solo header
        { headers: { Authorization: `Bearer ${token}` } }
      );

      // Debug: Respuesta del backend
      console.log("Respuesta backend login:", response.data);

      // Guarda el token de respuesta (puede ser el mismo idToken) en localStorage
      localStorage.setItem("jwtToken", response.data.token);

      // Redirige a App_cliente.js (asume que la ruta es "/app-cliente")
      navigate("/app-cliente");

    } catch (err) {
      // Debug: Error completo
      console.error("Error en login:", err);

      // Si el backend responde con 401 y algún mensaje
      let mensaje = "Error en login: ";
      if (err.response) {
        if (err.response.data) {
          mensaje += typeof err.response.data === "string"
            ? err.response.data
            : (err.response.data.message || JSON.stringify(err.response.data));
        } else {
          mensaje += "Respuesta vacía del servidor";
        }
      } else {
        mensaje += err.message;
      }
      setError(mensaje);
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <h2>Login</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <input
        type="email"
        placeholder="Email"
        required
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Contraseña"
        required
        value={contrasena}
        onChange={(e) => setContrasena(e.target.value)}
      />
      <button type="submit">Iniciar sesión</button>
      <p>¿No tienes cuenta? <Link to="/register">Regístrate</Link></p>
    </form>
  );
};

export default Login;
