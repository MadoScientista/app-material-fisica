
-- Inserción nuevos tipos de evento para el ms suscripciones
INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('SUSCRIPCION_NUEVA', ' ha creado una nueva suscripción para el usuario id: ', true),
('SUSCRIPCION_CANCELADA', ' ha cancelado la suscripción del usuario id: ', true),
('SUSCRIPCION_EXPIRADA', 'ha expirado la suscripción del usuario id: ', true),
('SUSCRIPCION_ACTUALIZADA', ' ha actualizado la suscripción del usuario id: ', true);