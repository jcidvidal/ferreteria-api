// src/components/AuthPage.js

import React, { useState } from "react";
import Login from "./Login";
import Register from "./Register";

function AuthPage() {
  const [showRegister, setShowRegister] = useState(false);

  return (
    <div>
      {showRegister ? (
        <>
          <Register />
          <p>
            ¿Ya tienes cuenta?{" "}
            <button onClick={() => setShowRegister(false)}>Iniciar sesión</button>
          </p>
        </>
      ) : (
        <>
          <Login />
          <p>
            ¿No tienes cuenta?{" "}
            <button onClick={() => setShowRegister(true)}>Registrarse</button>
          </p>
        </>
      )}
    </div>
  );
}

export default AuthPage;
