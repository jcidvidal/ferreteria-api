// src/components/Login.js
import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const user = userCredential.user;

      // Obtener el token de Firebase (lo puedes enviar al backend si necesitas)
      const token = await user.getIdToken();

      // Opcional: puedes guardar el token en localStorage para usarlo en el backend
      localStorage.setItem("token", token);

      // OPCIÓN 1: Redirigir por correo si es simple
      if (email === "admin@tudominio.com") {
        window.location.href = "/admin";
        localStorage.setItem("role", "admin");
      } else {
        window.location.href = "/cliente";
        localStorage.setItem("role", "cliente");
      }

      // OPCIÓN 2: Si quieres roles avanzados, usa Firestore para guardar info extra del usuario (te ayudo si lo necesitas)
    } catch (err) {
      setError("Usuario o contraseña incorrecta");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Iniciar sesión</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <input
        type="email"
        placeholder="Correo electrónico"
        value={email}
        onChange={e => setEmail(e.target.value)}
        required
      />
      <input
        type="password"
        placeholder="Contraseña"
        value={password}
        onChange={e => setPassword(e.target.value)}
        required
      />
      <button type="submit">Entrar</button>
    </form>
  );
};

export default Login;
