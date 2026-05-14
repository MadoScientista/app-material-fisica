-- Creación tablas microservicio logros

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema logros_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `logros_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `logros_db` ;

-- -----------------------------------------------------
-- Table `logros_db`.`tipo_logro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logros_db`.`tipo_logro` (
  `id_tipo_logro` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `criterio` VARCHAR(50) NULL DEFAULT NULL,
  `operador` VARCHAR(5) NULL DEFAULT NULL,
  `umbral` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id_tipo_logro`),
  UNIQUE INDEX `UQ_tipo_logro_nombre` (`nombre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `logros_db`.`logro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logros_db`.`logro` (
  `completado` BIT(1) NULL DEFAULT NULL,
  `fecha_completado` DATETIME(6) NULL DEFAULT NULL,
  `id_logro` BIGINT NOT NULL AUTO_INCREMENT,
  `id_tipo_logro` BIGINT NULL DEFAULT NULL,
  `id_usuario` BIGINT NOT NULL,
  PRIMARY KEY (`id_logro`),
  INDEX `FK_logro_tipo_logro` (`id_tipo_logro`),
  CONSTRAINT `FK_logro_tipo_logro`
    FOREIGN KEY (`id_tipo_logro`)
    REFERENCES `logros_db`.`tipo_logro` (`id_tipo_logro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `logros_db`.`recuento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `logros_db`.`recuento` (
  `id_recuento` BIGINT NOT NULL AUTO_INCREMENT,
  `id_usuario` BIGINT NOT NULL,
  `n_comunidades` BIGINT NOT NULL,
  `n_ejercicios_compartidos` BIGINT NOT NULL,
  `n_ejercicios_creados` BIGINT NOT NULL,
  PRIMARY KEY (`id_recuento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
