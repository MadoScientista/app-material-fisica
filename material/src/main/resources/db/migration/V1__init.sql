
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema material_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `material_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `material_db` ;


-- -----------------------------------------------------
-- Table `material_db`.`item_ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `material_db`.`item_ejercicio` (
  `fecha_creacion` DATETIME NOT NULL,
  `id_item_ejercicio` BIGINT NOT NULL AUTO_INCREMENT,
  `id_usuario_creador` BIGINT NOT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `texto_ejercicios` TEXT NOT NULL,
  `titulo` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id_item_ejercicio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `material_db`.`material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `material_db`.`material` (
  `fecha_creacion` DATETIME NULL DEFAULT NULL,
  `id_material` BIGINT NOT NULL AUTO_INCREMENT,
  `id_usuario_creador` BIGINT NOT NULL,
  PRIMARY KEY (`id_material`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `material_db`.`item_en_material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `material_db`.`item_en_material` (
  `id_item_ejercicio` BIGINT NOT NULL,
  `id_material` BIGINT NOT NULL,
  INDEX `FK54kp60um3248q4gim211u3si8` (`id_item_ejercicio`),
  INDEX `FKolrwa8fbvgtfvpqhm9dwrrv7d` (`id_material`),
  CONSTRAINT `FK_ITEM_EN_MATERIAL_ID_ITEM_EJERCICIO`
    FOREIGN KEY (`id_item_ejercicio`)
    REFERENCES `material_db`.`item_ejercicio` (`id_item_ejercicio`),
  CONSTRAINT `FK_ITEM_EN_MATERIAL_ID_MATERIAL`
    FOREIGN KEY (`id_material`)
    REFERENCES `material_db`.`material` (`id_material`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
