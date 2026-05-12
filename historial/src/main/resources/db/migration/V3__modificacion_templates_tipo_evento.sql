
-- Templates para inserción de ids con string format
UPDATE tipo_evento SET descripcion = 'El usuario %d se ha registrado como nuevo usuario.' WHERE nombre = 'USUARIO_CREADO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha modificado su perfil.' WHERE nombre = 'USUARIO_ACTUALIZADO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha creado un ejercicio para los usuarios: %s.' WHERE nombre = 'EJERCICIO_CREADO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha compartido un ejercicio con los usuarios: %s.' WHERE nombre = 'EJERCICIO_COMPARTIDO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha dejado de compartir un ejercicio con los usuarios: %s.' WHERE nombre = 'EJERCICIO_DEJADO_COMPARTIR';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha eliminado un ejercicio.' WHERE nombre = 'EJERCICIO_ELIMINADO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha resuelto un ejercicio.' WHERE nombre = 'EJERCICIO_RESUELTO';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha creado una nueva suscripción para los usuarios: %s.' WHERE nombre = 'SUSCRIPCION_NUEVA';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha cancelado la suscripción de los usuarios: %s.' WHERE nombre = 'SUSCRIPCION_CANCELADA';
UPDATE tipo_evento SET descripcion = 'Ha expirado la suscripción de los usuarios: %s.' WHERE nombre = 'SUSCRIPCION_EXPIRADA';
UPDATE tipo_evento SET descripcion = 'El usuario %d ha actualizado la suscripción de los usuarios: %s.' WHERE nombre = 'SUSCRIPCION_ACTUALIZADA';