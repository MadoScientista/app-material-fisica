-- V1__init.sql

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema historial_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `historial_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `historial_db` ;

-- -----------------------------------------------------
-- Table `historial_db`.`tipo_evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `historial_db`.`tipo_evento` (
  `notificacion_activa` BIT(1) NOT NULL,
  `id_tipo_evento` BIGINT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(200) NULL DEFAULT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_tipo_evento`),
  UNIQUE INDEX `UKcw144edf2de22f267efr0j59k` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `historial_db`.`evento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `historial_db`.`evento` (
  `id_evento` BIGINT NOT NULL AUTO_INCREMENT,
  `id_tipo_evento` BIGINT NOT NULL,
  `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `id_usuario_origen` BIGINT NOT NULL,
  `descripcion` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_evento`),
  INDEX `FKh8qotjbabxa76li4be7dalbx5` (`id_tipo_evento` ASC) VISIBLE,
  CONSTRAINT `FKh8qotjbabxa76li4be7dalbx5`
    FOREIGN KEY (`id_tipo_evento`)
    REFERENCES `historial_db`.`tipo_evento` (`id_tipo_evento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Insertar datos en la tabla tipo_evento
-- -----------------------------------------------------

INSERT INTO tipo_evento (nombre, descripcion, notificacion_activa) VALUES
('USUARIO_CREADO', ' ha registado como usuario un nuevo usuario con id: ', false),
('USUARIO_ACTUALIZADO', ' ha modificado el perfil del usuario con id: ', false),
('EJERCICIO_CREADO', ' ha generado un ejercicio para el usuario id: ', true),
('EJERCICIO_COMPARTIDO', ' ha compartido un ejercicio con los usuarios id: ', true),
('EJERCICIO_DEJADO_COMPARTIR', ' ha dejado de compartir un ejercicio con los usuarios con id: ', true),
('EJERCICIO_ELIMINADO', ' ha eliminado un ejercicio del usuario con id: ', false),
('EJERCICIO_RESUELTO', ' ha resuelto un ejercicio del usuario con id: ', true);
