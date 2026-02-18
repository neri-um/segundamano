# Especificación del Proyecto

## Visión General del Proyecto
**Segundamano** es una plataforma distribuida diseñada para facilitar la compraventa de productos de segunda mano dentro de una comunidad. El sistema permite a los usuarios publicar productos, gestionarlos y buscarlos mediante filtros avanzados, organizándolos en una estructura jerárquica de categorías.

El proyecto abarca el desarrollo completo, desde la persistencia de datos y lógica de negocio hasta la capa de presentación web.

## Modelo de Dominio
El sistema gestiona las siguientes entidades principales con sus respectivos atributos:

*   **Usuario**: Identificador, email (login), nombre, apellidos, clave, fecha de nacimiento, teléfono y rol (administrador/usuario).
*   **Producto**: Identificador, título, descripción, precio, estado (Enumerado: *nuevo, como nuevo, buen estado, aceptable, para piezas*), fecha de publicación, visualizaciones, disponibilidad de envío.
*   **Categoría**: Estructura jerárquica (árbol) con identificador, nombre, descripción y categorías hijas.
*   **Lugar de Recogida**: Geolocalización (latitud/longitud) y descripción asociada a un producto.

## Arquitectura y Stack Tecnológico
El desarrollo sigue una arquitectura por capas estricta basada en principios de diseño de software:

*   **Lenguaje**: Java 17.
*   **Gestión de Dependencias**: Apache Maven.
*   **Capa de Persistencia**: JPA (Java Persistence API) con Hibernate.
    *   Uso de **Patrón Repositorio** y **Repositorios AdHoc** para separar consultas complejas.
    *   Base de datos: MySQL.
*   **Capa de Negocio**: Patrón Servicio (`ServicioUsuarios`, `ServicioProductos`, `ServicioCategorias`).
*   **Capa de Presentación**: JavaServer Faces (JSF) con Facelets.
*   **Componentes UI**: PrimeFaces.
*   **Intercambio de Datos**: JAXB para importación de catálogos XML.

## Requisitos Funcionales

### Módulo de Gestión (Backend y Servicios)

#### Gestión de Usuarios (`ServicioUsuarios`)
*   **Registro**: Alta de nuevos usuarios con validación de datos.
*   **Autenticación**: Validación de credenciales (email/clave).
*   **Modificación**: Actualización de perfil (datos personales, contacto).

#### Gestión de Categorías (`ServicioCategorias`)
*   **Importación Masiva**: Carga de jerarquías de categorías desde ficheros XML externos.
*   **Consulta Jerárquica**: Recuperación de categorías raíz y navegación por subcategorías (descendientes).
*   **Administración**: Modificación de descripciones de categorías existentes.

#### Gestión de Productos (`ServicioProductos`)
*   **Ciclo de Vida**: Alta y modificación (precio/descripción) de productos.
*   **Geolocalización**: Asignación de lugares de recogida mediante coordenadas.
*   **Analítica**:
    *   Registro de visualizaciones por producto.
    *   Generación de informes mensuales ("Historial del Mes") ordenados por popularidad.
*   **Búsqueda Avanzada**: Motor de búsqueda con filtros dinámicos:
    *   Por categoría (incluyendo subcategorías recursivamente).
    *   Por texto en descripción.
    *   Por estado de conservación.
    *   Por precio máximo.

### Interfaz de Usuario (Frontend JSF)

La aplicación web debe proporcionar las siguientes vistas y flujos de navegación:

1.  **Acceso y Seguridad**:
    *   Inicio de sesión de usuarios.
    *   Cierre de sesión seguro.
2.  **Dashboard de Vendedor**:
    *   Formulario de creación de productos.
    *   Listado de "Mis Productos" con resumen de métricas (vistas, estado).
    *   Edición rápida de productos propios.
3.  **Marketplace (Público/Comprador)**:
    *   **Buscador**: Interfaz de filtrado multicriterio.
    *   **Listado de Resultados**: Vista de catálogo con información clave (Precio, Estado, Título).
    *   **Ficha de Detalle**: Vista completa del producto, vendedor y ubicación, que incrementa automáticamente el contador de visitas.


