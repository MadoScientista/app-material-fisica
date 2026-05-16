-- Inserta tipos de notificaci�n para eventos del microservicio comunidades
-- COMUNIDAD_CREADA (idTipoEvento = 16)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(16, 'Comunidad creada', 'Has creado una nueva comunidad', 'push'),
(16, 'Comunidad creada', 'Felicidades {usuarioOrigen}, has creado una nueva comunidad', 'web');

-- MIEMBRO_AGREGADO (idTipoEvento = 17)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(17, 'Miembro agregado', 'Te han agregado a una comunidad', 'push'),
(17, 'Miembro agregado', '{usuarioOrigen} te ha agregado a una comunidad', 'web');

-- MIEMBRO_ELIMINADO (idTipoEvento = 18)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(18, 'Miembro eliminado', 'Has sido eliminado de una comunidad', 'push'),
(18, 'Miembro eliminado', 'Has sido eliminado de una comunidad', 'web');
