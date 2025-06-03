import React from "react";
import "./Inicio.css";

const Inicio = () => {
  return (
    <div className="inicio-container">
      <div className="inicio-bienvenida">
        <h1>Bienvenido al Sistema de Gestion de Ferreteria</h1>
        <p>Administra tus productos, clientes, ventas y más desde un solo lugar.</p>
        <div className="inicio-botones">
          <button onClick={() => window.location.href = "/Stock"}>Ver Stock</button>
          <button onClick={() => window.location.href = "/Clientes"}>Clientes</button>
          <button onClick={() => window.location.href = "/Ventas"}>Ventas</button>
          <button onClick={() => window.location.href = "/ReclamosSugerencias/Reclamos.js"}>Reclamos</button>
        </div>
      </div>
    </div>
  );
};

export default Inicio;


