import React, { useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import { useNavigate } from "react-router-dom";

const Login = ({ onRegisterClick }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const user = userCredential.user;
      const token = await user.getIdToken();
      localStorage.setItem("token", token);

      // Redirección por correo
      if (email === "admin@tudominio.com") {
        window.location.href = "/Admin";
        localStorage.setItem("role", "admin");
      } else {
        window.location.href = "cliente/App.js";
        localStorage.setItem("role", "cliente");
      }
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
      <p>
        ¿No tienes cuenta?{""}
        <button
          type="button"
          onClick={() => navigate("/Register")} >
        Registrarse
        </button>
      </p>
    </form>
  );
};

export default Login;
