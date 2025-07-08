import React, { useState, useEffect } from "react";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "../css/Login_style.css"; // Usa el mismo CSS

const Register = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nombre, setNombre] = useState("");
  const [telefono, setTelefono] = useState("");
  const [rol, setRol] = useState("CLIENTE");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      // Registrar en Firebase Auth
      const userCredential = await createUserWithEmailAndPassword(auth, email, password);
      const user = userCredential.user;
      const token = await user.getIdToken();

      // Registrar en el back-end (Spring Boot)
      await axios.post(
        "http://localhost:8080/api/auth/register",
        {
          email,
          rol,
          nombre,
          telefono,
          contrasena: password
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setSuccess("¡Usuario registrado correctamente!");
      setTimeout(() => navigate("/"), 2000);

    } catch (err) {
      if (err.response && err.response.data) {
        setError(
          typeof err.response.data === "string"
            ? err.response.data
            : err.response.data.message || JSON.stringify(err.response.data)
        );
      } else {
        setError(err.message || "Error en el registro");
      }
      console.error("Error en registro:", err);
    }
  };

  return (
    <div className="login-unix-bg">
      <form onSubmit={handleSubmit} className="login-unix-container">
        <h2 className="login-unix-title">REGISTRO</h2>
        {error && <p style={{ color: "red", marginBottom: "6px" }}>{error}</p>}
        {success && <p style={{ color: "green", marginBottom: "6px" }}>{success}</p>}

        <input
          className="login-unix-input"
          type="email"
          placeholder="Correo electrónico"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <input
          className="login-unix-input"
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <input
          className="login-unix-input"
          type="text"
          placeholder="Nombre completo"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />

        <input
          className="login-unix-input"
          type="text"
          placeholder="Teléfono (ej: +56912345678)"
          value={telefono}
          onChange={(e) => setTelefono(e.target.value)}
          required
        />

        <select
          className="login-unix-input"
          value={rol}
          onChange={(e) => setRol(e.target.value)}
        >
          <option value="cliente">Cliente</option>
          <option value="admin">Administrador</option>
        </select>

        <button className="login-unix-btn" type="submit">Registrarse</button>
        <div className="login-unix-small">
          ¿Ya tienes una cuenta? <Link to="/">Iniciar sesión</Link>
        </div>
      </form>
    </div>
  );
};

export default Register;
