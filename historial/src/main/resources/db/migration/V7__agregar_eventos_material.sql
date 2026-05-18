-- Inserta tipos de evento MATERIAL para el microservicio material

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('ITEM_EJERCICIO_CREADO', 'El usuario %d ha creado un nuevo item de ejercicio.', true),
('ITEM_EJERCICIO_ACTUALIZADO', 'El usuario %d ha actualizado un item de ejercicio.', false),
('ITEM_EJERCICIO_ELIMINADO', 'El usuario %d ha eliminado un item de ejercicio.', false),
('MATERIAL_CREADO', 'El usuario %d ha creado un nuevo material didactico.', true),
('MATERIAL_ACTUALIZADO', 'El usuario %d ha actualizado un material didactico.', false),
('MATERIAL_ELIMINADO', 'El usuario %d ha eliminado un material didactico.', false);
