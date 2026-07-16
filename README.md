# 🛒 SegundaMano — Plataforma de Compraventa de Segunda Mano

Proyecto de arquitectura de microservicios desarrollado para la asignatura **Arquitectura del Software** (ArSO) de la Universidad de Murcia.

**Autores:** Irene Moreno · Juan Aledo

---

## 📐 Visión General de la Arquitectura

El sistema está construido como un conjunto de **4 microservicios independientes** que se comunican entre sí mediante HTTP (Retrofit2) y mensajería asíncrona (RabbitMQ). Todos los servicios se despliegan juntos mediante **Docker Compose**.

```
                        ┌──────────────────────────────────────┐
                        │           PASARELA (: 8090)          │
                        │   Spring Boot + Netflix Zuul         │
                        │   OAuth2 GitHub + JWT                │
                        └──────┬────────┬──────────┬───────────┘
                               │        │          │
               ┌───────────────┘        │          └────────────────┐
               ▼                        ▼                           ▼
   ┌───────────────────┐   ┌───────────────────┐   ┌───────────────────────┐
   │  USUARIOS (:8081) │   │ PRODUCTOS (:9090) │   │ COMPRAVENTAS (:8083)  │
   │  Java EE / JAX-RS │   │  Spring Boot      │   │  Spring Boot          │
   │  MySQL            │   │  MySQL + HATEOAS  │   │  MongoDB + HATEOAS    │
   └───────────────────┘   └─────────┬─────────┘   └──────────┬────────────┘
                                     │                         │
                              ┌──────▼─────────────────────────▼──────┐
                              │             RabbitMQ (:5672)           │
                              │   Eventos: producto vendido, etc.      │
                              └────────────────────────────────────────┘
```

### Tecnologías aplicadas

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 11 |
| API REST (usuarios) | JAX-RS 2.x (Jersey 2.41) + EclipseLink JPA |
| API REST (productos, compraventas, pasarela) | Spring Boot 2.6 |
| Autenticación interna | JWT (jjwt 0.9.1) |
| Login externo | OAuth2 con GitHub (Spring Security) |
| Proxy / API Gateway | Netflix Zuul (Spring Cloud) |
| Mensajería asíncrona | RabbitMQ 3 (AMQP) |
| Hipermedia | Spring HATEOAS (HAL) |
| Documentación API | SpringDoc OpenAPI / Swagger UI |
| Llamadas entre microservicios | Retrofit2 + Gson/Jackson |
| Base de datos relacional | MySQL 8 |
| Base de datos documental | MongoDB |
| Contenerización | Docker + Docker Compose |
| Exploración MongoDB | Mongo Express (:8084) |

---

## 🗂️ Estructura del Repositorio

```
segundamano/
├── docker-compose.yml          # Orquestación completa del sistema
├── mysql/
│   └── init.sql/
│       └── 01-init.sql         # Esquema inicial: db_usuarios + db_productos
├── usuarios/                   # Microservicio Usuarios (Java EE / JAX-RS)
├── productos-spring/           # Microservicio Productos (Spring Boot)
├── compraventas-spring/        # Microservicio Compraventas (Spring Boot)
├── pasarela/                   # API Gateway (Spring Boot + Zuul)
└── postman/                    # Colección Postman con pruebas
```

Cada microservicio contiene su propio `Dockerfile` para construir la imagen independientemente.

---

## 🐳 Despliegue con Docker Compose

### Servicios levantados

| Servicio | Imagen / Build | Puerto host | Puerto interno | Descripción |
|---|---|---|---|---|
| `mysql` | `mysql:8` | 3306 | 3306 | Base de datos relacional compartida |
| `mongo` | `mongo` | 27017 | 27017 | Base de datos documental |
| `mongo-express` | `mongo-express` | 8084 | 8081 | UI web para MongoDB |
| `rabbitmq` | `rabbitmq:3-management` | 5672 / 15672 | 5672 / 15672 | Broker de mensajes + panel de gestión |
| `usuarios` | `./usuarios` | 8081 | 8080 | Microservicio de usuarios |
| `productos` | `./productos-spring` | 9090 | 9090 | Microservicio de productos |
| `compraventas` | `./compraventas-spring` | 8083 | 8083 | Microservicio de compraventas |
| `pasarela` | `./pasarela` | 8090 | 8090 | API Gateway único punto de entrada |

### Arrancar el sistema

```bash
# Primera vez (construye todas las imágenes y levanta los contenedores)
docker compose up --build

# Arranques posteriores
docker compose up

# Detener y eliminar contenedores (los volúmenes de datos se conservan)
docker compose down

# Eliminar también los datos persistidos (MySQL y MongoDB)
docker compose down -v
```

> ⚠️ El script `mysql/init.sql/01-init.sql` **sólo se ejecuta la primera vez** que se crea el volumen `mysql_data`. Si necesitas reinicializar la base de datos, elimina el volumen con `docker compose down -v`.

### Healthchecks configurados

- **MySQL**: `mysqladmin ping` cada 10 s, hasta 5 reintentos. Los servicios `usuarios` y `productos` esperan a que MySQL esté sano antes de arrancar.
- **RabbitMQ**: `rabbitmq-diagnostics check_port_connectivity` cada 10 s, hasta 10 reintentos.

---

## 🗄️ Base de Datos

### MySQL — `db_usuarios`

```sql
CREATE TABLE usuarios (
    id              VARCHAR(36) PRIMARY KEY,  -- UUID
    email           VARCHAR(255) UNIQUE NOT NULL,
    nombre          VARCHAR(255) NOT NULL,
    apellidos       VARCHAR(255) NOT NULL,
    clave           VARCHAR(255) NOT NULL,     -- contraseña (hash)
    fechaNacimiento DATE NOT NULL,
    telefono        VARCHAR(20) NOT NULL,
    admin           TINYINT(1) DEFAULT 0,
    contadorVentas  INT DEFAULT 0,
    contadorCompras INT DEFAULT 0,
    githubLogin     VARCHAR(255) NULL          -- login de GitHub para OAuth2
);
```

Usuario administrador precargado: `admin@example.com` / `discordmod`.

### MySQL — `db_productos`

```sql
CREATE TABLE categorias (
    id          VARCHAR(255) PRIMARY KEY,
    nombre      VARCHAR(255) UNIQUE NOT NULL,
    descripcion TEXT,
    ruta        VARCHAR(255) NOT NULL,
    padre_id    VARCHAR(255) NULL REFERENCES categorias(id)  -- árbol jerárquico
);
```

Categorías precargadas: **Ropa**, **Electrónica**, **Deportes**.

La tabla de `productos` es gestionada automáticamente por **Spring Data JPA** (Hibernate) al arrancar el microservicio.

### MongoDB — `compraventas`

Las compraventas se almacenan como documentos en la colección `compraventas`. Cada documento guarda un snapshot desnormalizado con los datos del producto, vendedor y comprador en el momento de la transacción.

---

## 👤 Microservicio Usuarios

**Tecnología:** Java EE · JAX-RS (Jersey) · EclipseLink JPA · RabbitMQ AMQP  
**Puerto interno:** 8080 → mapeado a **8081** en el host  
**Base de datos:** MySQL `db_usuarios`

### Arquitectura interna

Sigue el patrón **Hexagonal (Puertos y Adaptadores)**:

```
usuarios/src/main/java/
├── usuarios/
│   ├── modelo/          # Entidades de dominio (Usuario)
│   ├── puertos/         # Interfaces de entrada y salida
│   ├── servicio/        # Lógica de negocio (IServicioUsuarios)
│   ├── repositorio/     # Acceso a datos JPA
│   ├── adaptadores/     # Adaptadores externos (RabbitMQ publisher)
│   └── rest/            # Controladores JAX-RS + filtros JWT
│       ├── UsuariosControladorRest.java
│       ├── AutenticacionControladorRest.java
│       ├── JwtFiltroToken.java      # Filtro que valida el JWT en cada request
│       └── dto/                     # DTOs de entrada y salida
├── servicio/            # FactoriaServicios (DI manual)
└── repositorio/         # Excepción EntidadNoEncontrada
```

### Endpoints REST

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/usuarios` | Pública | Registro de nuevo usuario |
| `POST` | `/usuarios/credenciales` | Pública | Verificación de credenciales (uso interno de la pasarela) |
| `GET` | `/usuarios` | `USUARIO` | Listado completo de usuarios |
| `GET` | `/usuarios/{id}` | `USUARIO` | Obtener perfil de un usuario |
| `GET` | `/usuarios/{id}/publico` | Pública | Datos básicos (para llamadas internas entre microservicios) |
| `GET` | `/usuarios/github/{login}` | Pública | Buscar usuario por su login de GitHub (OAuth2) |
| `PATCH` | `/usuarios/{id}` | `USUARIO` (propio) | Modificar datos del propio perfil |
| `POST` | `/auth/login` | Pública | Login con email/contraseña → devuelve JWT |

### Seguridad JWT

- El endpoint `/auth/login` valida credenciales y emite un **JWT firmado** con el `id` del usuario como `subject` y su rol (`USUARIO` / `ADMINISTRADOR`) como claim.
- El filtro `JwtFiltroToken` intercepta todas las peticiones, extrae el token del header `Authorization: Bearer <token>`, lo valida y establece el `SecurityContext`.
- Las rutas marcadas con `@PermitAll` (registro, login, `/publico`, `/github`) no requieren token.
- Las rutas con `@RolesAllowed("USUARIO")` exigen token válido.

---

## 📦 Microservicio Productos

**Tecnología:** Spring Boot 2.6 · Spring Data JPA · Spring Security · Spring HATEOAS · RabbitMQ · Retrofit2  
**Puerto interno y externo:** **9090**  
**Base de datos:** MySQL `db_productos`

### Arquitectura interna

Organizada en capas según **Domain-Driven Design (DDD)**:

```
productos-spring/src/main/java/productos/
├── dominio/
│   └── modelo/          # Entidades: Producto, Categoria, Estado (enum), UsuarioSimplificado
├── aplicacion/
│   ├── puertos/
│   │   ├── entrada/     # IServicioProductos, IServicioCategorias
│   │   └── salida/      # UsuarioPuerto (llamada a microservicio usuarios)
│   ├── servicio/        # Implementación de lógica de negocio
│   └── dto/             # DTOs de aplicación
├── infraestructura/
│   ├── rest/
│   │   ├── ProductosController.java   # Controlador REST principal
│   │   ├── CategoriasController.java  # CRUD de categorías
│   │   ├── ProductosAPI.java          # Interfaz para Swagger
│   │   └── config/                    # Spring Security + JWT filter
│   └── adaptadores/     # Adaptador Retrofit2 → microservicio usuarios
└── eventos/             # Publicador/consumidor de eventos RabbitMQ
```

### Endpoints REST

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/productos` | `USUARIO` | Publicar un nuevo producto |
| `GET` | `/productos` | Pública | Buscar productos (filtros: `categoria`, `descripcion`, `estado`, `precioMax`) con **paginación** |
| `GET` | `/productos/{id}` | Pública | Obtener detalle de un producto (con link HATEOAS `self`) |
| `PATCH` | `/productos/{id}` | `USUARIO` (propietario) | Modificar precio o descripción |
| `PATCH` | `/productos/{id}/recogida` | `USUARIO` (propietario) | Asignar punto de recogida (latitud, longitud, descripción) |
| `PATCH` | `/productos/{id}/visualizaciones` | Pública | Incrementar contador de visualizaciones |
| `GET` | `/productos/historial` | Pública | Historial de productos vendidos en un mes (`?anyo=&mes=`) con **paginación** |
| `GET` | `/categorias` | Pública | Listado de categorías |
| `GET` | `/categorias/{id}` | Pública | Detalle de una categoría |

### Características destacadas

- **Paginación completa** con `Pageable` (parámetros `page`, `size`, `sort`) y respuesta `PagedModel` con links HATEOAS (`self`, `next`, `prev`).
- **HATEOAS**: cada recurso `Producto` incluye un link `self` hacia su propia URL.
- **Swagger UI** disponible en `http://localhost:9090/swagger-ui.html`.
- **Verificación de propietario**: un usuario solo puede modificar sus propios productos (se compara el `id` del JWT con el `idVendedor` del producto).
- **Integración con Usuarios**: al crear un producto, llama vía Retrofit2 al endpoint `/usuarios/{id}/publico` para obtener los datos del vendedor.

---

## 🤝 Microservicio Compraventas

**Tecnología:** Spring Boot 2.6 · Spring Data MongoDB · Spring Security · Spring HATEOAS · RabbitMQ · Retrofit2  
**Puerto interno y externo:** **8083**  
**Base de datos:** MongoDB `compraventas`

### Arquitectura interna

```
compraventas-spring/src/main/java/compraventas/
├── dominio/
│   └── modelo/          # Entidad Compraventa (documento MongoDB)
├── aplicacion/
│   ├── puertos/
│   │   └── entrada/     # IServicioCompraventas
│   └── servicio/        # Lógica de negocio: orquesta llamadas a productos y usuarios
├── infraestructura/
│   ├── rest/
│   │   ├── CompraventaController.java  # Controlador REST
│   │   ├── dto/                         # Request y Response DTOs
│   │   ├── excepciones/                 # @ControllerAdvice para errores
│   │   └── config/                      # Spring Security + JWT filter
│   └── adaptadores/     # Clientes Retrofit2 hacia Productos y Usuarios
└── eventos/             # Consumidor RabbitMQ (actualiza contadores de usuarios)
```

### Endpoints REST

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/api/compraventas` | `USUARIO` (comprador) | Crear una compraventa |
| `GET` | `/api/compraventas/{id}` | Pública | Obtener una compraventa por ID (con link HATEOAS `self`) |
| `GET` | `/api/compraventas/comprador/{idComprador}` | `USUARIO` (propio) | Historial de compras de un usuario (paginado) |
| `GET` | `/api/compraventas/vendedor/{idVendedor}` | `USUARIO` (propio) | Historial de ventas de un usuario (paginado) |
| `GET` | `/api/compraventas/comprador/{idComprador}/vendedor/{idVendedor}` | `ADMINISTRADOR` | Transacciones entre dos usuarios concretos (paginado) |

### Flujo de una compraventa

1. El comprador realiza `POST /api/compraventas` con `idProducto` e `idComprador`.
2. El servicio llama a **Productos** vía Retrofit2 para obtener los datos del producto y verificar que está disponible.
3. Llama a **Usuarios** para obtener los datos del comprador y del vendedor.
4. Persiste el documento en **MongoDB** con un snapshot de todos los datos.
5. Publica un **evento en RabbitMQ** notificando la venta.
6. El microservicio de Productos consume el evento y actualiza el estado del producto a `VENDIDO`.
7. El microservicio de Usuarios consume el evento y actualiza los contadores `contadorVentas` y `contadorCompras`.

---

## 🔀 Pasarela (API Gateway)

**Tecnología:** Spring Boot 2.2 · Netflix Zuul · Spring Security · OAuth2 Client · JWT  
**Puerto:** **8090**

La pasarela actúa como **único punto de entrada** al sistema. Todas las peticiones externas pasan por ella, que:

1. **Autentica** al usuario (JWT propio o OAuth2 con GitHub).
2. **Enruta** la petición al microservicio correspondiente via Zuul.
3. **Propaga** el JWT al microservicio destino para que pueda validar el rol.

### Rutas configuradas (Zuul)

| Prefijo externo | Microservicio destino | Puerto interno |
|---|---|---|
| `/usuarios/**` | `usuarios` | 8080 |
| `/productos/**` | `productos` | 9090 |
| `/compraventas/**` | `compraventas` | 8083 |

### Autenticación OAuth2 con GitHub

La pasarela implementa el flujo **Authorization Code** de OAuth2 con GitHub:

1. El usuario accede a `/oauth2/authorization/github`.
2. GitHub redirige de vuelta con un código de autorización.
3. La pasarela intercambia el código por un token de acceso y obtiene el perfil del usuario de GitHub.
4. Busca el `githubLogin` del usuario en el microservicio de Usuarios (`GET /usuarios/github/{login}`).
5. Si existe, genera un **JWT propio** del sistema y lo devuelve al cliente.

---

## 🐇 Mensajería Asíncrona (RabbitMQ)

El sistema usa RabbitMQ para **desacoplar** los microservicios en operaciones que no requieren respuesta inmediata.

| Evento | Publicador | Consumidores | Efecto |
|---|---|---|---|
| Producto vendido | Compraventas | Productos, Usuarios | Marcar producto como `VENDIDO`; incrementar contadores de compra/venta |
| Alta de usuario | Usuarios | — | Notificación (extensible) |

El **panel de administración de RabbitMQ** es accesible en `http://localhost:15672` (usuario: `guest` / contraseña: `guest`).

---

## 🧪 Colección Postman

En el directorio `postman/` se incluye la colección de pruebas lista para importar en Postman.

### Usuarios de prueba

| Nombre | Email | Contraseña | Rol |
|---|---|---|---|
| Laura García | `laura@example.com` | `Password2` | USUARIO |
| Juan Aledo | `swagdogeman@example.com` | `skibidi` | USUARIO |
| Irene Moreno | `admin@example.com` | `discordmod` | ADMINISTRADOR |

### Variables de entorno Postman

La colección usa variables que se actualizan automáticamente tras cada request:

| Variable | Descripción |
|---|---|
| `token` | JWT obtenido al hacer login; se inyecta en `Authorization: Bearer {{token}}` |
| `idComprador` | ID del usuario comprador (Laura) |
| `idVendedor` | ID del usuario vendedor (Juan) |
| `idAdmin` | ID del administrador (Irene) |
| `idProducto` | ID del producto creado en las pruebas |
| `idCategoria` | ID de la categoría usada (`cat-deportes`) |
| `compraventa` | ID de la compraventa creada |

---

## 🔒 Seguridad

El sistema implementa **dos capas de seguridad**:

### 1. Autenticación (quién eres)
- **Login clásico**: `POST /auth/login` → devuelve JWT firmado con rol y userId.
- **OAuth2 GitHub**: flujo completo gestionado por la pasarela.

### 2. Autorización (qué puedes hacer)
- **`USUARIO`**: puede gestionar sus propios productos y ver su historial de compras/ventas.
- **`ADMINISTRADOR`**: puede consultar transacciones entre cualquier par de usuarios.
- **Verificación de propietario**: además del rol, se comprueba que el `subject` del JWT coincide con el recurso que se intenta modificar (ej.: un usuario no puede editar el producto de otro).
- **Endpoints internos** (`/publico`, `/credenciales`): sin autenticación, sólo accesibles desde dentro de la red Docker.

---

## 📊 Patrones de Diseño Aplicados

| Patrón | Dónde se aplica |
|---|---|
| **Hexagonal (Ports & Adapters)** | Todos los microservicios: separación entre dominio, aplicación e infraestructura |
| **API Gateway** | Pasarela Zuul como punto de entrada único |
| **Event-Driven Architecture** | RabbitMQ para comunicación asíncrona entre servicios |
| **HATEOAS** | Respuestas de Productos y Compraventas con links de navegación |
| **DTO (Data Transfer Object)** | Todos los servicios usan DTOs separados del modelo de dominio |
| **Repository** | Spring Data JPA (Productos) y Spring Data MongoDB (Compraventas) |
| **Snapshot desnormalizado** | La compraventa guarda una copia de los datos en el momento de la transacción |
| **Health Check + Depends On** | Docker Compose espera a que MySQL y RabbitMQ estén listos antes de arrancar los servicios |
