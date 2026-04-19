# ArSO 2025-26 — Proyecto compraventa de segunda mano

Proyecto de arquitectura de microservicios desarrollado para la asignatura **Arquitectura del Software** (ArSO).

Autores: **Irene Moreno** y **Juan Aledo**

***

## Despliegue

| Componente | Tecnología | Base de datos | Puerto |
|---|---|---|---|
| `usuarios` | Java EE / JAX-RS (Tomcat) | MySQL (`db_usuarios`) | 8081 |
| `productos` | Spring Boot | MySQL (`db_productos`) | 8082 |
| `compraventas` | Spring Boot | MongoDB | 8083 |
| `pasarela` | Spring Boot / Zuul | — | 8080 |
| `rabbitmq` | RabbitMQ | — | 5672 / 15672 |
| `mysql` | MySQL 8 | `db_usuarios`, `db_productos` | 3306 |
| `mongodb` | MongoDB | `db_compraventas` | 27017 |

***


## Microservicio Productos


| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/productos` | USUARIO | Alta de producto |
| `GET` | `/productos/{id}` | Pública | Recuperar producto |
| `PATCH` | `/productos/{id}` | USUARIO (propietario) | Modificar producto |
| `PATCH` | `/productos/{id}/recogida` | USUARIO (propietario) | Asignar recogida |
| `POST` | `/productos/{id}/visualizaciones` | Pública | Añadir visualización |
| `GET` | `/productos/historial` | Pública | Historial del mes |
| `GET` | `/productos` | Pública | Buscar productos a la venta |

***

## Microservicio Compraventas


| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/compraventas` | USUARIO (comprador) | Crear compraventa |
| `GET` | `/compraventas/compras/{idUsuario}` | USUARIO (propio) | Ver compras de un usuario |
| `GET` | `/compraventas/ventas/{idUsuario}` | USUARIO (propio) | Ver ventas de un usuario |
| `GET` | `/compraventas` | ADMINISTRADOR | Compraventas entre dos usuarios |



## Microservicio Usuarios

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/usuarios` | Pública | Alta de usuario |
| `GET` | `/usuarios` | USUARIO | Listado de usuarios |
| `GET` | `/usuarios/{id}` | USUARIO | Recuperar usuario |
| `PATCH` | `/usuarios/{id}` | USUARIO (propio) | Modificar datos |
| `POST` | `/auth/login` | Pública | Login (devuelve JWT) |



## Postman

### Usuarios de prueba

| Nombre | Email | Clave | Rol |
|---|---|---|---|
| Laura García | `laura@example.com` | `Password2` | USUARIO |
| Irene Moreno | `admin@example.com` | `discordmod` | ADMINISTRADOR |
| Juan Aledo | `swagdogeman@example.com` | `skibidi` | USUARIO |

### Variables globales
Se actualizan automáticamente según quien haga login.

| Variable | Descripción |
|---|---|
| `token` | JWT obtenido al hacer login |
| `idCategoria` | ID de categoría de prueba (Deporte) |
| `idVendedor` | ID del vendedor (Juan) |
| `idProducto` | ID del producto creado (Bici)|
| `compraventa` | ID de la compraventa creada |
| `idComprador` | ID del comprador (Laura) |
| `idAdmin` | ID del usuario administrador (Irene)|

***
