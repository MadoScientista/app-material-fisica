-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: generador_ejercicios_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contexto_fisico`
--

DROP TABLE IF EXISTS `contexto_fisico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contexto_fisico` (
  `id_contexto_fisico` int NOT NULL AUTO_INCREMENT,
  `m_max` double NOT NULL,
  `m_min` double NOT NULL,
  `v_max` int NOT NULL,
  `v_min` int NOT NULL,
  `x_max` int NOT NULL,
  `x_min` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_contexto_fisico`),
  UNIQUE KEY `UK27yves2i10d6jrlkvjv95c2vj` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contexto_fisico`
--

LOCK TABLES `contexto_fisico` WRITE;
/*!40000 ALTER TABLE `contexto_fisico` DISABLE KEYS */;
/*!40000 ALTER TABLE `contexto_fisico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dificultad`
--

DROP TABLE IF EXISTS `dificultad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dificultad` (
  `id_dificultad` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_dificultad`),
  UNIQUE KEY `UKj63bapowo4b85map0im764dwx` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dificultad`
--

LOCK TABLES `dificultad` WRITE;
/*!40000 ALTER TABLE `dificultad` DISABLE KEYS */;
/*!40000 ALTER TABLE `dificultad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magnitud_fisica`
--

DROP TABLE IF EXISTS `magnitud_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `magnitud_fisica` (
  `id_magnitud_fisica` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `simbolo` varchar(255) NOT NULL,
  PRIMARY KEY (`id_magnitud_fisica`),
  UNIQUE KEY `UKiqhejb9ce52pg4dp4lk1pohyh` (`nombre`),
  UNIQUE KEY `UKpl3hq5koii0gyrp5p1ov0fnnk` (`simbolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magnitud_fisica`
--

LOCK TABLES `magnitud_fisica` WRITE;
/*!40000 ALTER TABLE `magnitud_fisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `magnitud_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plantilla_enunciado`
--

DROP TABLE IF EXISTS `plantilla_enunciado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plantilla_enunciado` (
  `id_contexto_fisico` int NOT NULL,
  `id_incognita` int NOT NULL,
  `id_tema_fisica` int NOT NULL,
  `resultado_positivo` bit(1) NOT NULL,
  `id_plantilla_enunciado` bigint NOT NULL AUTO_INCREMENT,
  `enunciado` tinytext NOT NULL,
  PRIMARY KEY (`id_plantilla_enunciado`),
  KEY `FKli1jxi9f19w537lxrom6i5am8` (`id_contexto_fisico`),
  KEY `FK2afo1j6iahx5dksrl4657wxiw` (`id_incognita`),
  KEY `FK86uj6u6skkdk88g4r2sl5r3gr` (`id_tema_fisica`),
  CONSTRAINT `FK2afo1j6iahx5dksrl4657wxiw` FOREIGN KEY (`id_incognita`) REFERENCES `variable_fisica` (`id_variable_fisica`),
  CONSTRAINT `FK86uj6u6skkdk88g4r2sl5r3gr` FOREIGN KEY (`id_tema_fisica`) REFERENCES `tema_fisica` (`id_tema_fisica`),
  CONSTRAINT `FKli1jxi9f19w537lxrom6i5am8` FOREIGN KEY (`id_contexto_fisico`) REFERENCES `contexto_fisico` (`id_contexto_fisico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantilla_enunciado`
--

LOCK TABLES `plantilla_enunciado` WRITE;
/*!40000 ALTER TABLE `plantilla_enunciado` DISABLE KEYS */;
/*!40000 ALTER TABLE `plantilla_enunciado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tema_fisica`
--

DROP TABLE IF EXISTS `tema_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tema_fisica` (
  `id_tema_fisica` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_tema_fisica`),
  UNIQUE KEY `UK279gh3dk3asrar9vnm8pcds96` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tema_fisica`
--

LOCK TABLES `tema_fisica` WRITE;
/*!40000 ALTER TABLE `tema_fisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `tema_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidad_de_medida`
--

DROP TABLE IF EXISTS `unidad_de_medida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidad_de_medida` (
  `es_basesi` bit(1) NOT NULL,
  `essi` bit(1) NOT NULL,
  `factor_conversionsi` double NOT NULL,
  `id_magnitud_fisica` int NOT NULL,
  `id_unidad_de_medida` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `simbolo` varchar(255) NOT NULL,
  PRIMARY KEY (`id_unidad_de_medida`),
  UNIQUE KEY `UKlbr4ujr0it2q5agdb73e6um2i` (`nombre`),
  UNIQUE KEY `UKt4oncl8j0q3au7cw4c78dwx6d` (`simbolo`),
  KEY `FKdsi4uvs9vurmuudykmsr959kb` (`id_magnitud_fisica`),
  CONSTRAINT `FKdsi4uvs9vurmuudykmsr959kb` FOREIGN KEY (`id_magnitud_fisica`) REFERENCES `magnitud_fisica` (`id_magnitud_fisica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidad_de_medida`
--

LOCK TABLES `unidad_de_medida` WRITE;
/*!40000 ALTER TABLE `unidad_de_medida` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidad_de_medida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variable_fisica`
--

DROP TABLE IF EXISTS `variable_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variable_fisica` (
  `id_magnitud_fisica` int NOT NULL,
  `id_variable_fisica` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `simbolo` varchar(255) NOT NULL,
  PRIMARY KEY (`id_variable_fisica`),
  UNIQUE KEY `UKe0cb0h14ogq6gqjba0x2fxiqk` (`nombre`),
  UNIQUE KEY `UKcvjikjk11njo47fiii6sqjede` (`simbolo`),
  KEY `FK546j4usrtu2u0u49ktmxcgea4` (`id_magnitud_fisica`),
  CONSTRAINT `FK546j4usrtu2u0u49ktmxcgea4` FOREIGN KEY (`id_magnitud_fisica`) REFERENCES `magnitud_fisica` (`id_magnitud_fisica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variable_fisica`
--

LOCK TABLES `variable_fisica` WRITE;
/*!40000 ALTER TABLE `variable_fisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `variable_fisica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-29  1:14:52
