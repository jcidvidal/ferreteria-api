import React from "react";
import "./Inicio.css";
import { useNavigate } from "react-router-dom";

const Inicio = () => {
  const navigate = useNavigate();

  return (
    <div className="inicio-container">
      <div className="inicio-bienvenida">
        <h1>Bienvenido al Sistema de Gestión de Ferretería</h1>
        <p>Administra tus productos, clientes, ventas y más desde un solo lugar.</p>
        <div className="inicio-botones">
          <button onClick={() => navigate("/Stock")}>Ver Stock</button>
          <button onClick={() => navigate("/Clientes")}>Clientes</button>
          <button onClick={() => navigate("/Ventas")}>Ventas</button>
          <button onClick={() => navigate("/Reclamos")}>Reclamos</button>
        </div>
      </div>
    </div>
  );
};

export default Inicio;


