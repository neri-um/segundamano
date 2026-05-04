CREATE DATABASE IF NOT EXISTS db_usuarios
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_productos
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_usuarios;

CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(36) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    admin TINYINT(1) NOT NULL DEFAULT 0,
    contadorVentas INT NOT NULL DEFAULT 0,
    contadorCompras INT NOT NULL DEFAULT 0,
    githubLogin VARCHAR(255) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email)
) ENGINE=InnoDB;

INSERT INTO usuarios (
    id, email, nombre, apellidos, clave,
    fechaNacimiento, telefono, admin,
    contadorVentas, contadorCompras, githubLogin
)
SELECT UUID(), 'admin@example.com', 'Irene', 'Moreno', 'discordmod',
       '1990-01-01', '600000000', 1, 0, 0, NULL
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE email = 'admin@example.com'
);

USE db_productos;

CREATE TABLE IF NOT EXISTS categorias (
    id VARCHAR(255) NOT NULL,
    descripcion TEXT,
    nombre VARCHAR(255) NOT NULL,
    ruta VARCHAR(255) NOT NULL,
    padre_id VARCHAR(255) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_categorias_nombre (nombre),
    CONSTRAINT fk_categorias_padre FOREIGN KEY (padre_id)
        REFERENCES categorias(id)
) ENGINE=InnoDB;

INSERT INTO categorias (id, descripcion, nombre, ruta, padre_id)
SELECT 'cat-ropa', 'Ropa y accesorios', 'Ropa', '/ropa', NULL
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE id = 'cat-ropa');

INSERT INTO categorias (id, descripcion, nombre, ruta, padre_id)
SELECT 'cat-electronica', 'Productos electrónicos', 'Electrónica', '/electronica', NULL
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE id = 'cat-electronica');

INSERT INTO categorias (id, descripcion, nombre, ruta, padre_id)
SELECT 'cat-deportes', 'Artículos deportivos', 'Deportes', '/deportes', NULL
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE id = 'cat-deportes');
