import React from 'react';
import styles from './Clientes.module.css';

const Clientes = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Gestión de Clientes</h1>
      <p className={styles.subtitle}>Aquí puedes administrar los clientes registrados</p>
    </div>
  );
};

export default Clientes;
