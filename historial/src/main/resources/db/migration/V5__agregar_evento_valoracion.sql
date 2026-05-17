-- Inserta tipos de evento VALORACION para el microservicio valoraciones

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('VALORACION_CREADA', 'El usuario %d ha valorado un ejercicio.', true),
('VALORACION_ACTUALIZADA', 'El usuario %d ha actualizado su valoración.', true),
('VALORACION_ELIMINADA', 'El usuario %d ha eliminado su valoración.', false);
