import React, { useState } from "react";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // 🔴 INICIO: LÓGICA TEMPORAL PARA SIMULAR LOGIN 🔴
    const userMock = {
      email,
      tipo: email === "admin@admin.com" ? "admin" : "cliente"
    };

    if (userMock.tipo === "admin") {
      window.location.href = "http://localhost:3001/";
    } else {
      window.location.href = "http://localhost:3000/";
    }
    // 🔴 FIN DE LÓGICA TEMPORAL 🔴
  };

  return React.createElement("div", { style: { maxWidth: 400, margin: "auto", padding: 20 } },
    React.createElement("h2", null, "Iniciar sesión"),
    React.createElement("form", { onSubmit: handleSubmit },
      React.createElement("input", {
        type: "email",
        placeholder: "Correo",
        value: email,
        onChange: (e) => setEmail(e.target.value),
        required: true,
        style: { display: "block", marginBottom: 10, width: "100%" }
      }),
      React.createElement("input", {
        type: "password",
        placeholder: "Contraseña",
        value: password,
        onChange: (e) => setPassword(e.target.value),
        required: true,
        style: { display: "block", marginBottom: 10, width: "100%" }
      }),
      React.createElement("button", { type: "submit", style: { width: "100%" } }, "Ingresar")
    )
  );
}

export default Login;