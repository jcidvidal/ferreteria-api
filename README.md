# Ferretería Pfeifer 🛠️
**Digitalización y Gestión Integral para Negocios Ferreteros** 

**Ferretería Pfeifer** es una aplicación web diseñada específicamente para digitalizar el proceso de ventas y administración en una ferretería física. Gracias a su arquitectura, permite que tanto clientes como administradores interactúen en tiempo real. El sistema optimiza la gestión del inventario y el control de las compras para asegurar un funcionamiento eficiente y moderno del negocio.

---

## 🚀 Stack Tecnológico

### Backend (Arquitectura de Capas)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

* **Seguridad:** Implementación de filtros **JWT** para autenticación y manejo de roles.
* **Persistencia:** Repositorios para el acceso y gestión de datos en la nube.

### Frontend (Arquitectura React)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

* **Comunicación:** Uso de **Axios** para solicitudes HTTP (POST, DELETE) al backend.
* **Componentes Clave:** * `App.js`: Gestiona la carga de datos y funciones globales.
    * `UserForm.js`: Captura datos de usuario y realiza envíos mediante solicitudes POST.
    * `UserList.js`: Visualiza la información y gestiona eliminaciones mediante solicitudes DELETE.

### Integraciones
![Mercado Pago](https://img.shields.io/badge/Mercado_Pago-009EE3?style=for-the-badge&logo=mercadopago&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)

---

## ✨ Funcionalidades Principales

* **Para Clientes:** Permite comprar productos de ferretería en línea y recibir boletas digitales.
* **Para Administradores:** Facilita la gestión integral de productos y el estricto control de stock.
* **Seguridad:** Control de accesos mediante autenticación y roles para restringir la visibilidad según el perfil del usuario.
* **Gestión de Ventas:** Registro, cálculo automático de totales y generación de comprobantes de pago en PDF.

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
| **Factory** | Creación de objetos complejos con lógica adicional. |
| **Util / Security** | Utilidades de encriptación, validación de JWT y reglas de acceso. |

---

## 🧪 Calidad de Código (Unit Testing)
El proyecto incluye pruebas unitarias robustas para garantizar la fiabilidad de los procesos críticos:
* **AdminService:** Verificación de existencia de usuarios, modificación de stock y control de permisos.
* **BoletaService:** Lógica de manejo de ventas, cálculo de totales y gestión de excepciones por boletas cerradas.
* **CompraService:** Registro de transacciones, actualización de métodos de pago y obtención de comprobantes en PDF.
* **MercadoPagoService:** Pruebas de integración para el procesamiento de pagos mediante webhooks.

---

## 👥 Equipo y Contexto Académico
Este proyecto fue desarrollado para la asignatura de **Programación Orientada a Objetos** con entrega el 09/07/2025:
* **Juan Pablo Cid Vidal** ([@jcidvidal](https://github.com/jcidvidal)) - Arquitectura Backend, Seguridad y Lógica.
* **Jorge Pfeifer** ([@JorgePfeifer](https://github.com/JorgePfeifer)) - Desarrollo y Lógica.
* **Marco Venegas** ([@m4c4c0-spec](https://github.com/m4c4c0-spec)) - Desarrollo y Frontend.
