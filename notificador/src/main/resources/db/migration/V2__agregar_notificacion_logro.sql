-- Inserta tipos de notificación para LOGRO_COMPLETADO (idTipoEvento = 12)

INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(12, 'Logro completado', '¡Felicidades! Has completado un logro.', 'push'),
(12, 'Logro completado', '¡Felicidades {usuarioOrigen}, has completado un logro!', 'web');
