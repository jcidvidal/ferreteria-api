import React from "react";
import "./Inicio.css";

const Inicio = () => {
  return (
    <div className="inicio-container">
      <div className="inicio-bienvenida">
        <h1>Bienvenido al Sistema de Gestion de Ferreteria</h1>
        <p>Administra tus productos, clientes, ventas y más desde un solo lugar.</p>
        <div className="inicio-botones">
          <button onClick={() => window.location.href = "/stock"}>Ver Stock</button>
          <button onClick={() => window.location.href = "/clientes"}>Clientes</button>
          <button onClick={() => window.location.href = "/ventas"}>Ventas</button>
          <button onClick={() => window.location.href = "/reclamos"}>Reclamos</button>
        </div>
      </div>
    </div>
  );
};

export default Inicio;


