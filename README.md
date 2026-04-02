# Ferretería Pfeifer 🛠️
**Digitalización y Gestión Integral para Negocios Ferreteros**

**Ferretería Pfeifer** es una aplicación web diseñada específicamente para digitalizar el proceso de ventas y administración en una ferretería física. Gracias a su arquitectura, permite que tanto clientes como administradores interactúen en tiempo real. El sistema optimiza la gestión del inventario y el control de las compras para asegurar un funcionamiento eficiente y moderno del negocio.

---

## 🚀 Stack Tecnológico

### Backend (Arquitectura de Capas)
* **Lenguaje:** Java.
* **Framework:** Spring Boot.
* **Seguridad:** Spring Security con filtros **JWT** para autenticación y manejo de roles.
* **Persistencia:** Repositorios para acceso a datos en la nube.
* **Integraciones:** Configuración para servicios de **Mercado Pago** y **Firebase**.

### Frontend (React)
* **Framework:** React con **JavaScript**.
* **Comunicación:** Axios para solicitudes HTTP (POST, DELETE) al backend.
* **Componentes Clave:** * `App.js`: Corazón del frontend que gestiona la carga de datos y funciones globales.
    * `UserForm.js`: Captura datos de usuario y realiza envíos mediante solicitudes POST.
    * `UserList.js`: Visualiza la información y gestiona eliminaciones mediante solicitudes DELETE.

---

## ✨ Funcionalidades Principales

* **Para Clientes:** Permite comprar productos de ferretería en línea y recibir boletas digitales.
* **Para Administradores:** Facilita la gestión integral de productos y el control de stock.
* **Seguridad:** Implementa control de accesos mediante autenticación y roles para restringir la visibilidad de la información
* **Gestión de Ventas:** Registro, cálculo de totales y generación de comprobantes de pago en PDF.

---

## 🏗️ Arquitectura del Software
El sistema sigue una estructura profesional para asegurar escalabilidad y orden:

| Capa / Componente | Responsabilidad |
| :--- | :--- |
| **Controller** | Punto de entrada que define los endpoints consumidos por el frontend. |
| **DTO** | Encargado de la transferencia y control de la información en la red. |
| **Model** | Representa los objetos de la lógica de negocio (Usuario, Producto, Compra, etc.). |
| **Service** | Orquestador de la lógica de negocio y procesos complejos. |
| **Repository** | Responsable de la persistencia y el acceso a los datos. |
| **Factory** | Creación de objetos complejos o abstractos con lógica adicional. |
| **Util / Security** | Utilidades de encriptación, validación de JWT y reglas de acceso. |

---

## 🧪 Calidad de Código (Unit Testing)
El proyecto incluye pruebas unitarias robustas para garantizar la fiabilidad de los procesos críticos:
* **AdminService:** Verificación de existencia de usuarios, modificación de stock y control de permiso].
* **BoletaService:** Lógica de manejo de ventas, cálculo de totales y gestión de excepciones para boletas cerradas.
* **CompraService:** Registro de transacciones, actualización de métodos de pago y obtención de comprobantes en PDF.
* **MercadoPagoService:** Pruebas de integración para el procesamiento de pagos mediante webhooks.

---

## 👥 Equipo y Contexto Académico
Este proyecto fue desarrollado para la asignatura de **Programación Orientada a Objetos** con fecha de entrega el 09/07/2025:
* **Juan Pablo Cid Vidal** ([@jcidvidal](https://github.com/jcidvidal)) - Arquitectura Backend, Seguridad y Lógica.
* **Jorge Pfeifer** ([@JorgePfeifer](https://github.com/JorgePfeifer)) - Desarrollo y Lógica de Frontend.
* **Marco Venegas** ([@m4c4c0-spec](https://github.com/m4c4c0-spec)) - Desarrollo y Frontend.
