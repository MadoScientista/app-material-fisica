-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema suscripciones_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `suscripciones_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `suscripciones_db` ;

-- -----------------------------------------------------
-- Table `suscripciones_db`.`tipo_suscripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suscripciones_db`.`tipo_suscripcion` (
  `id_tipo_suscripcion` BIGINT NOT NULL AUTO_INCREMENT,
  `n_max_ejercicios` BIGINT NOT NULL,
  `precio_por_mes` BIGINT NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_tipo_suscripcion`),
  UNIQUE INDEX `UK6010tehsqo1hvwc8yd6lty64` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `suscripciones_db`.`suscripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suscripciones_db`.`suscripcion` (
  `activo` BIT(1) NOT NULL,
  `fecha_inicio` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `id_suscripcion` BIGINT NOT NULL AUTO_INCREMENT,
  `id_tipo_suscripcion` BIGINT NULL DEFAULT NULL,
  `id_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id_suscripcion`),
  INDEX `FKjrg3dn9abk8bapxfrbjeofr0s` (`id_tipo_suscripcion` ASC) VISIBLE,
  CONSTRAINT `FKjrg3dn9abk8bapxfrbjeofr0s`
    FOREIGN KEY (`id_tipo_suscripcion`)
    REFERENCES `suscripciones_db`.`tipo_suscripcion` (`id_tipo_suscripcion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Poblado tabla tipo_suscripcion
-- -----------------------------------------------------
INSERT INTO tipo_suscripcion (nombre, n_max_ejercicios, precio_por_mes) VALUES
('GRATUITA', 3, 0),
('PREMIUM', 999999, 4990);
