CREATE TABLE IF NOT EXISTS actividades (
    id VARCHAR(36) PRIMARY KEY,
    nombre VARCHAR(255),
    estado VARCHAR(50),
    fecha_hora DATETIME
);