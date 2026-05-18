-- Inserta tipos de evento LOGO para el microservicio logos

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('LOGO_CREADO', 'El usuario %d ha creado un nuevo logo.', false),
('LOGO_ACTUALIZADO', 'El usuario %d ha actualizado un logo.', false),
('LOGO_ELIMINADO', 'El usuario %d ha eliminado un logo.', false);
