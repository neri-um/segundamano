# segundamano

Plataforma de compraventa de productos de segunda mano. Proyecto desarrollado para la asignatura de Aplicaciones Distribuidas'25.

## Funcionalidades
- **Usuarios**: Registro, autenticación y gestión de perfil.
- **Productos**: Publicación, edición y listado de productos a la venta.
- **Categorías**: Organización jerárquica de productos.
- **Búsqueda**: Filtrado por categoría, precio y estado.

## Arquitectura y Diseño
El proyecto sigue una arquitectura en capas basada en principios SOLID para garantizar escalabilidad y mantenibilidad:

- **Patrón Repositorio y AdHoc**: Implementación de repositorios personalizados (AdHoc) para separar consultas complejas JPA de la interfaz estándar CRUD, desacoplando la persistencia del dominio.
- **Separación de Responsabilidades**: Servicios dedicados (ServicioUsuarios, ServicioProductos) que encapsulan reglas de negocio, evitando God Classes.

Ver documentación técnica detallada aquí: [Especificaciones del proyecto](docs/MEMORIA.md)

## Tecnologías
- **Lenguaje**: Java 17
- **Construcción**: Maven
- **Persistencia**: JPA
- **Base de Datos**: MySQL
- **Control de Versiones**: Git

## Instalación y Ejecución

### Requisitos previos
- Java JDK 17 o superior
- Maven 3.6+
- MySQL Server corriendo en el puerto 3306

### Pasos para ejecutar
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/neri-um/segundumTusApellidos.git
   ```

2. Configurar la base de datos en `src/main/resources/META-INF/persistence.xml`.

3. Compilar el proyecto:
   ```bash
   cd segundamano
   mvn clean install
   ```
   
## Autora
- **Irene M.** - [GitHub](https://github.com/neri-um)
