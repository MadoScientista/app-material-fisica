-- Inserta tipos de evento COMUNIDAD para el microservicio comunidades

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('COMUNIDAD_CREADA', ' ha creado una nueva comunidad', true),
('MIEMBRO_AGREGADO', ' ha agregado miembros a la comunidad', true),
('MIEMBRO_ELIMINADO', ' ha eliminado miembros de la comunidad', true);
