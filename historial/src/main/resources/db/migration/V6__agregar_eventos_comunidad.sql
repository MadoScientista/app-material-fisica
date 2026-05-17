-- Inserta tipos de evento COMUNIDAD para el microservicio comunidades

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('COMUNIDAD_CREADA', 'El usuario %d ha creado una nueva comunidad para los usuarios: %s.', true),
('MIEMBRO_AGREGADO', 'El usuario %d ha agregado a los usuarios: %s.', true),
('MIEMBRO_ELIMINADO', 'El usuario %d ha eliminado a los usuarios: %s.', true);
