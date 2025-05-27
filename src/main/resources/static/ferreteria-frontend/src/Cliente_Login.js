import React, { useState, useContext } from "react";
import CryptoJS from "crypto-js";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom"; // <-- Importa el hook

const SECRET_KEY = "MARCO1313$";

function Login() {
  const { login } = useContext(AuthContext);
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // <-- Usa el hook aquí

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    const encryptedPassword = CryptoJS.AES.encrypt(form.password, SECRET_KEY).toString();
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: form.email, password: encryptedPassword })
      });
      if (!res.ok) throw new Error("Usuario o contraseña incorrectos");
      const data = await res.json();
      login(data); // Guarda el usuario en contexto
      navigate("/productos"); // <-- Redirige a la ruta que quieras
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <h2>Iniciar Sesión</h2>
      <form onSubmit={handleSubmit}>
        <label>Email:</label>
        <input type="email" name="email" value={form.email} onChange={handleChange} required />
        <label>Contraseña:</label>
        <input type="password" name="password" value={form.password} onChange={handleChange} required />
        <button type="submit">Ingresar</button>
        {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}
      </form>
    </div>
  );
}

export default Login;
