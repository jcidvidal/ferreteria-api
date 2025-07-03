import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const token = await userCredential.user.getIdToken();

      const response = await axios.post("http://localhost:8080/api/auth/login", {}, {
        headers: { Authorization: `Bearer ${token}` }
      });

      localStorage.setItem("jwtToken", response.data.token);
      navigate("/ruta-protegida");
    } catch (err) {
      setError("Error en login: " + (err.response?.data || err.message));
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <h2>Login</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <input type="email" placeholder="Email" required onChange={(e) => setEmail(e.target.value)} />
      <input type="password" placeholder="Contraseña" required onChange={(e) => setPassword(e.target.value)} />
      <button type="submit">Iniciar sesión</button>

      <p>¿No tienes cuenta? <Link to="/register">Regístrate</Link></p>
    </form>
  );
};

export default Login;
