-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema usuarios_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema usuarios_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `usuarios_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `usuarios_db` ;

-- -----------------------------------------------------
-- Table `usuarios_db`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuarios_db`.`usuario` (
  `id_usuario` BIGINT NOT NULL AUTO_INCREMENT,
  `apellido` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `nombre` VARCHAR(255) NULL DEFAULT NULL,
  `nombre_usuario` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `UK5171l57faosmj8myawaucatdw` (`email` ASC) VISIBLE,
  UNIQUE INDEX `UKpuhr3k3l7bj71hb7hk7ktpxn0` (`nombre_usuario` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `usuarios_db`.`ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuarios_db`.`ejercicio` (
  `fecha` DATE NULL DEFAULT NULL,
  `id_creador` BIGINT NULL DEFAULT NULL,
  `id_ejercicio` BIGINT NOT NULL AUTO_INCREMENT,
  `dificultad` VARCHAR(255) NULL DEFAULT NULL,
  `enunciado` TEXT NOT NULL,
  `incognita` VARCHAR(255) NULL DEFAULT NULL,
  `respuesta` VARCHAR(255) NULL DEFAULT NULL,
  `tema` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_ejercicio`),
  INDEX `FKlxoc49be6rcgx1etotr48c4n8` (`id_creador` ASC) VISIBLE,
  CONSTRAINT `FKlxoc49be6rcgx1etotr48c4n8`
    FOREIGN KEY (`id_creador`)
    REFERENCES `usuarios_db`.`usuario` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `usuarios_db`.`ejercicio_compartido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuarios_db`.`ejercicio_compartido` (
  `id_ejercicio` BIGINT NOT NULL,
  `id_usuario` BIGINT NOT NULL,
  INDEX `FKi6ptfxjvnc7j8xe1yocl51wxr` (`id_usuario` ASC) VISIBLE,
  INDEX `FK7pdiuuttcl7p54u94lon5fij` (`id_ejercicio` ASC) VISIBLE,
  CONSTRAINT `FK7pdiuuttcl7p54u94lon5fij`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `usuarios_db`.`ejercicio` (`id_ejercicio`),
  CONSTRAINT `FKi6ptfxjvnc7j8xe1yocl51wxr`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `usuarios_db`.`usuario` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
