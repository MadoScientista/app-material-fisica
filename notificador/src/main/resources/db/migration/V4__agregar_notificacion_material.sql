-- Inserta tipos de notificacion para eventos del microservicio material

-- ITEM_EJERCICIO_CREADO (idTipoEvento = 19)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(19, 'Item de ejercicio creado', 'Nuevo item de ejercicio creado', 'push'),
(19, 'Item de ejercicio creado', 'Felicidades {usuarioOrigen}, has creado un nuevo item de ejercicio', 'web');

-- MATERIAL_CREADO (idTipoEvento = 22)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(22, 'Material creado', 'Nuevo material didactico creado', 'push'),
(22, 'Material creado', 'Felicidades {usuarioOrigen}, has creado un nuevo material didactico', 'web');
