-- V1__init.sql

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema valoraciones_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `valoraciones_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `valoraciones_db` ;

-- -----------------------------------------------------
-- Table `valoraciones_db`.`valoracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `valoraciones_db`.`valoracion` (
  `id_valoracion` BIGINT NOT NULL AUTO_INCREMENT,
  `id_ejercicio` BIGINT NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  `puntuacion` INT NOT NULL,
  `comentario` VARCHAR(500) NULL DEFAULT NULL,
  `fecha_creacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_valoracion`),
  UNIQUE INDEX `UK_ejercicio_usuario` (`id_ejercicio` ASC, `id_usuario` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
