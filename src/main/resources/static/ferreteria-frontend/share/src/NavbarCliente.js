import React from "react";
import { Link } from "react-router-dom";
import "./css/NavbarCliente.css"; 


function NavbarCliente() {
  return (
    <header className="navbar-cliente">
      <div className="navbar-top">

      <Link to="/">
          <img src="/assets/logo.png" alt="Ferretería Pfeifer" className="logo" />
        </Link>
        <nav className="menu-principal">
     <ul className="menu-principal">
        <li><Link to="/cliente/Herramientas">HERRAMIENTAS</Link></li>
        <li><Link to="/cliente/Materiales">MATERIALES</Link></li>
        <li><Link to="/cliente/Seguridad">SEGURIDAD</Link></li>
        <li><Link to="/cliente/Plomeria">PLOMERÍA</Link></li>
        <li><Link to="/cliente/Electricidad">ELECTRICIDAD</Link></li>
        <li><Link to="/cliente/Jardin">JARDÍN</Link></li>
        <li><Link to="/cliente/Contacto">CONTACTO</Link></li>
      </ul>
        </nav>
        <div className="icons">
          <Link to="/cliente/Usuario"><span role="img" aria-label="user">👤</span></Link>
          <Link to="/cliente/Carrito"><span role="img" aria-label="cart">🛒</span></Link>
        </div>
      </div>
      <div className="linea-naranja"></div>
    </header>
  );
}

export default NavbarCliente;
