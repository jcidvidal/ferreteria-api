import React from 'react';
import styles from './Inicio.module.css';

const Inicio = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Bienvenido al Panel de Administración</h1>
      <p className={styles.subtitle}>Desde aquí puedes gestionar los módulos del sistema</p>
    </div>
  );
};

export default Inicio;
