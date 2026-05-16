
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema comunidades_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comunidades_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `comunidades_db` ;

-- -----------------------------------------------------
-- Table `comunidades_db`.`comunidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comunidades_db`.`comunidad` (
  `id_comunidad` BIGINT NOT NULL AUTO_INCREMENT,
  `id_usuario_creador` BIGINT NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_comunidad`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `comunidades_db`.`comunidad_miembro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comunidades_db`.`comunidad_miembro` (
  `id_comunidad` BIGINT NOT NULL,
  `id_miembro` BIGINT NOT NULL,
  INDEX `FKnn2afv281dfxuycorbar4xvh9` (`id_comunidad`),
  CONSTRAINT `fk_comunidad_comunidad_miembro`
    FOREIGN KEY (`id_comunidad`)
    REFERENCES `comunidades_db`.`comunidad` (`id_comunidad`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
