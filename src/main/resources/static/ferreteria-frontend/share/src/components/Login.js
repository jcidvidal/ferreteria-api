import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        contrasena: password
      });

      const { token, rol } = res.data; // Asumiendo que backend envía el token y rol

      localStorage.setItem("token", token);  // Guarda el token
      localStorage.setItem("rol", rol);      // Guarda el rol

      // Redirección según rol
      if (rol === "admin") {
        navigate("/admin");
      } else if (rol === "cliente") {
        navigate("/cliente");
      } else {
        navigate("/");
      }

      alert("Inicio de sesión exitoso.");

    } catch (err) {
      if (err.response && err.response.data) {
        setError(
          typeof err.response.data === "string"
            ? err.response.data
            : err.response.data.message || JSON.stringify(err.response.data)
        );
      } else {
        setError("Error en inicio de sesión");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="email"
        placeholder="Email"
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
      <button type="submit">Iniciar Sesión</button>
      {error && (
        <p style={{ color: "red" }}>{error}</p>
      )}
      <p>
        ¿No tienes cuenta?
        <button type="button" onClick={() => navigate("/register")} style={{ marginLeft: 8 }}>
          Registrarse
        </button>
      </p>
    </form>
  );
};

export default Login;