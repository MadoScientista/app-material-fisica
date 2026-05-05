
-- Inserción nuevos tipos de evento para el ms suscripciones
INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('SUSCRIPCION_NUEVA', 'Se registra cuando se crea una nueva suscripción', true),
('SUSCRIPCION_CANCELADA', 'Se registra cuando se cancela una suscripción', true),
('SUSCRIPCION_EXPIRADA', 'Se registra cuando una suscripción ha excedido el plazo de validez', true),
('SUSCRIPCION_ACTUALIZADA', 'Se registra cuando se modifica el tipo de suscripción', true);