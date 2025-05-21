import React from "react";
import { Link } from "react-router-dom";
import "./NavbarCliente.css"; // crea este CSS para estilos personalizados

function NavbarCliente() {
  return (
    <header className="navbar-cliente">
      <div className="navbar-top">
      <Link to="/">
          <img src="/assets/logo.png" alt="Ferretería Pfeifer" className="logo" />
        </Link>
        <nav className="menu-principal">
          <ul>
            <li><Link to="/herramientas">HERRAMIENTAS</Link></li>
            <li><Link to="./materiales">MATERIALES</Link></li>
            <li><Link to="/seguridad">SEGURIDAD</Link></li>
            <li><Link to="/plomeria">PLOMERÍA</Link></li>
            <li><Link to="/electricidad">ELECTRICIDAD</Link></li>
            <li><Link to="/jardin">JARDÍN</Link></li>
            <li><Link to="/contacto">CONTACTO</Link></li>
          </ul>
        </nav>
        <div className="icons">
          <Link to="/perfil"><span role="img" aria-label="user">👤</span></Link>
          <Link to="/carrito"><span role="img" aria-label="cart">🛒</span></Link>
        </div>
      </div>
      <div className="linea-naranja"></div>
    </header>
  );
}

export default NavbarCliente;
