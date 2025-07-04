import React, { useState } from "react";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebaseConfig";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nombre, setNombre] = useState("");
  const [telefono, setTelefono] = useState("");
  const [rol, setRol] = useState("cliente"); // Usa 'rol' si tu backend espera 'rol'
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
      console.log('TOKEN JWT DE FIREBASE:', token); 

      // Registrar en el back-end (Spring Boot)
      await axios.post(
        "http://localhost:8080/api/auth/register",
        {
          email,
          rol,        // Cambia por 'role' si el backend espera ese campo, pero por tus modelos debería ser 'rol'
          nombre,
          telefono,
          contrasena: password // <-- nombre exacto según el DTO RegisterRequest
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
    <form onSubmit={handleSubmit}>
      <h2>Registro</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>{success}</p>}

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

      <input
        type="text"
        placeholder="Nombre completo"
        value={nombre}
        onChange={(e) => setNombre(e.target.value)}
        required
      />

      <input
        type="text"
        placeholder="Teléfono (ej: +56912345678)"
        value={telefono}
        onChange={(e) => setTelefono(e.target.value)}
        required
      />

      <select value={rol} onChange={(e) => setRol(e.target.value)}>
        <option value="cliente">Cliente</option>
        <option value="admin">Administrador</option>
      </select>

      <button type="submit">Registrarse</button>
    </form>
  );
};

export default Register;
