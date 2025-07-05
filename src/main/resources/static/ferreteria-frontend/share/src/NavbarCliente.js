import React from "react";
import { Link } from "react-router-dom";
import "./css/NavbarCliente.css"; 


function NavbarCliente() {
  return (
    <header className="navbar-cliente">
      <div className="navbar-top">

      <Link to="."> 
          <img src="/assets/logo.png" alt="Ferretería Pfeifer" className="logo" />
        </Link>
        <nav className="menu-principal">
     <ul className="menu-principal">
        <li><Link to="/cliente/productos/herramientas">HERRAMIENTAS</Link></li>
        <li><Link to="/cliente/productos/materiales">MATERIALES</Link></li>
        <li><Link to="/cliente/seguridad/Seguridad">SEGURIDAD</Link></li>
        <li><Link to="/cliente/productos/plomeria">PLOMERÍA</Link></li>
        <li><Link to="/cliente/productos/electricidad">ELECTRICIDAD</Link></li>
        <li><Link to="/cliente/productos/jardin">JARDÍN</Link></li>
        <li><Link to="/cliente/usuario/Contacto">CONTACTO</Link></li>
      </ul>
        </nav>
        <div className="icons">
          <Link to="/Usuario"><span role="img" aria-label="user">👤</span></Link>
          <Link to="/Carrito"><span role="img" aria-label="cart">🛒</span></Link>
        </div>
      </div>
      <div className="linea-naranja"></div>
    </header>
  );
}

export default NavbarCliente;
