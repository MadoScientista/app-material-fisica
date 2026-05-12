SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema notificador_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `notificador_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `notificador_db` ;

-- -----------------------------------------------------
-- Table `notificador_db`.`tipo_notificacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notificador_db`.`tipo_notificacion` (
  `id_tipo_evento` BIGINT NOT NULL,
  `id_tipo_notificacion` BIGINT NOT NULL AUTO_INCREMENT,
  `canal` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `plantilla_mensaje` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id_tipo_notificacion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `notificador_db`.`notificacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notificador_db`.`notificacion` (
  `leido` BIT(1) NOT NULL,
  `fecha_creacion` DATETIME(6) NOT NULL,
  `fecha_lectura` DATETIME(6) NULL DEFAULT NULL,
  `id_notificacion` BIGINT NOT NULL AUTO_INCREMENT,
  `id_tipo_notificacion` BIGINT NOT NULL,
  `id_usuario_destino` BIGINT NOT NULL,
  `id_usuario_origen` BIGINT NOT NULL,
  `mensaje` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_notificacion`),
  INDEX `FKid0hhjrcqjg7sqq3ls2mb5d4i` (`id_tipo_notificacion` ASC) VISIBLE,
  CONSTRAINT `FKid0hhjrcqjg7sqq3ls2mb5d4i`
    FOREIGN KEY (`id_tipo_notificacion`)
    REFERENCES `notificador_db`.`tipo_notificacion` (`id_tipo_notificacion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- EJERCICIO_CREADO (idTipoEvento = 3)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(3, 'Nuevo ejercicio generado', 'Nuevo ejercicio de física generado', 'push'),
(3, 'Nuevo ejercicio generado', 'Se ha generado un nuevo ejercicio sobre {temaEjercicio}', 'web');
-- EJERCICIO_COMPARTIDO (idTipoEvento = 4)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(4, 'Ejercicio compartido', 'Un ejercicio ha sido compartido contigo', 'push'),
(4, 'Ejercicio compartido', '{nombreUsuario} ha compartido un ejercicio sobre {temaEjercicio} contigo', 'web');
-- EJERCICIO_RESUELTO (idTipoEvento = 7)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(7, 'Ejercicio resuelto', 'Has resuelto un ejercicio', 'push'),
(7, 'Ejercicio resuelto', '¡Felicidades {nombreUsuario}, has resuelto correctamente el ejercicio sobre {temaEjercicio}!', 'web');
-- SUSCRIPCION_NUEVA (idTipoEvento = 8)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(8, 'Nueva suscripción', 'Tu suscripción ha sido activada', 'push'),
(8, 'Nueva suscripción', 'Hola {nombreUsuario}, tu suscripción ha sido activada exitosamente', 'web');
-- SUSCRIPCION_CANCELADA (idTipoEvento = 9)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(9, 'Suscripción cancelada', 'Tu suscripción ha sido cancelada', 'push'),
(9, 'Suscripción cancelada', 'Hola {nombreUsuario}, tu suscripción ha sido cancelada. Si fue un error, contáctanos', 'web');
-- SUSCRIPCION_EXPIRADA (idTipoEvento = 10)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(10, 'Suscripción expirada', 'Tu suscripción ha expirado', 'push'),
(10, 'Suscripción expirada', 'Hola {nombreUsuario}, tu suscripción ha expirado. Renueva para seguir accediendo a todas las funciones', 'web');
-- SUSCRIPCION_ACTUALIZADA (idTipoEvento = 11)
INSERT INTO tipo_notificacion (id_tipo_evento, descripcion, plantilla_mensaje, canal) VALUES
(11, 'Suscripción actualizada', 'Tu suscripción ha sido actualizada', 'push'),
(11, 'Suscripción actualizada', 'Hola {nombreUsuario}, tu suscripción ha sido actualizada exitosamente', 'web');