-- Inserta tipo de evento LOGRO_COMPLETADO para el microservicio logros

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('LOGRO_COMPLETADO', 'El usuario %d ha completado un logro.', true);
