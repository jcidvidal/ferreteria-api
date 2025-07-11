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

//NUEVO LOGIN PRPUESTO

/*import React, { useState } from "react";
  import { signInWithEmailAndPassword } from "firebase/auth";
  import { auth } from "../firebaseConfig";
  import { useNavigate } from "react-router-dom";
  import "../styles/Auth.css";

  const Login = () => {
    const [email, setEmail]       = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole]         = useState("cliente");  //  ← nuevo
    const [error, setError]       = useState("");
    const navigate = useNavigate();

    const handleSubmit = async e => {
      e.preventDefault();
      try {
        await signInWithEmailAndPassword(auth, email, password);
        navigate(role === "admin" ? "/admin" : "/cliente");   // ← redirección
      } catch (err) {
        setError("Credenciales inválidas.");
      }
    };

    return (
      <div className="auth‑container">
        <form className="auth‑form" onSubmit={handleSubmit}>
          <h2>Iniciar sesión</h2>

          {error && <p className="error">{error}</p>}

          <input
            type="email" placeholder="Correo electrónico" value={email}
            onChange={e => setEmail(e.target.value)} required
          />

          <input
            type="password" placeholder="Contraseña" value={password}
            onChange={e => setPassword(e.target.value)} required
          />

          <select value={role} onChange={e => setRole(e.target.value)}>
            <option value="cliente">Cliente</option>
            <option value="admin">Admin</option>
          </select>

          <button type="submit">Entrar</button>
        </form>
      </div>
    );
  };

  export default Login;
  */
