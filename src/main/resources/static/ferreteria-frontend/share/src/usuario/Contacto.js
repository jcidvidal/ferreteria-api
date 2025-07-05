import React, { useState } from 'react';
import emailjs from 'emailjs-com';
import '../css/Contacto.css';
import { db } from '../firebase/firebaseConfig';
import { collection, addDoc, serverTimestamp } from 'firebase/firestore';

function Contacto() {
  const [form, setForm] = useState({
    nombre: "",
    email: "",
    mensaje: "",
  });
  const [enviado, setEnviado] = useState(false);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();

    //  Guarda en Firestore
    try {
      await addDoc(collection(db, "contactos"), {
        ...form,
        fecha: serverTimestamp()
      });
    } catch (err) {
      alert("No se pudo guardar en Firebase: " + err.message);
    }

    //  Envía correo por EmailJS
    const serviceID = 'service_bod1imp';
    const templateID = 'template_avev5dh';
    const userID = 'MlS0bJzthvcwBfQi5';

    emailjs.send(serviceID, templateID, form, userID)
      .then(() => {
        setEnviado(true);
        setTimeout(() => setEnviado(false), 3500);
        setForm({ nombre: "", email: "", mensaje: "" });
      }, (error) => {
        alert('Error al enviar: ' + error.text);
      });
  };

  return (
    <div className="contacto-container">
      <h2 className="contacto-title">Contacto</h2>
      <form onSubmit={handleSubmit} autoComplete="off" className="contacto-form">
        <label>Nombre:</label>
        <input
          type="text"
          name="nombre"
          value={form.nombre}
          onChange={handleChange}
          required
        />
        <label>Correo electrónico:</label>
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
          required
        />
        <label>Mensaje:</label>
        <textarea
          name="mensaje"
          value={form.mensaje}
          onChange={handleChange}
          required
        />
        <button type="submit" className="contacto-btn">Enviar mensaje</button>
        {enviado && <div className="contacto-exito">¡Mensaje enviado correctamente!</div>}
      </form>
      <div className="contacto-info">
        <b>Dirección:</b> Avenida Los Pablos, Temuco<br />
        <b>Email:</b> jorge_pfeifer12@ferreteria.cl<br />
        <b>Teléfono:</b> +56 9 7895 7103
      </div>
    </div>
  );
}

export default Contacto;
