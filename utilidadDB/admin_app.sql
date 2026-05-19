DROP USER IF EXISTS 'admin_generador_ejercicios_db'@'localhost';
DROP USER IF EXISTS 'admin_historial_db'@'localhost';
DROP USER IF EXISTS 'admin_logros_db'@'localhost';
DROP USER IF EXISTS 'admin_material_db'@'localhost';
DROP USER IF EXISTS 'admin_notificador_db'@'localhost';
DROP USER IF EXISTS 'admin_suscripciones_db'@'localhost';
DROP USER IF EXISTS 'admin_usuarios_db'@'localhost';
DROP USER IF EXISTS 'admin_valoraciones_db'@'localhost';
DROP USER IF EXISTS 'admin_logos_db'@'localhost';
DROP USER IF EXISTS 'admin_comunidades_db'@'localhost';

DROP DATABASE IF EXISTS generador_ejercicios_db;
DROP DATABASE IF EXISTS historial_db;
DROP DATABASE IF EXISTS logros_db;
DROP DATABASE IF EXISTS material_db;
DROP DATABASE IF EXISTS notificador_db;
DROP DATABASE IF EXISTS suscripciones_db;
DROP DATABASE IF EXISTS usuarios_db;
DROP DATABASE IF EXISTS valoraciones_db;
DROP DATABASE IF EXISTS logos_db;
DROP DATABASE IF EXISTS comunidades_db;

CREATE DATABASE IF NOT EXISTS generador_ejercicios_db;
CREATE USER admin_generador_ejercicios_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON generador_ejercicios_db.* TO 'admin_generador_ejercicios_db'@'localhost';

CREATE DATABASE IF NOT EXISTS historial_db;
CREATE USER admin_historial_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON historial_db.* TO 'admin_historial_db'@'localhost';

CREATE DATABASE IF NOT EXISTS logros_db;
CREATE USER admin_logros_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON logros_db.* TO 'admin_logros_db'@'localhost';

CREATE DATABASE IF NOT EXISTS material_db;
CREATE USER admin_material_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON material_db.* TO 'admin_material_db'@'localhost';

CREATE DATABASE IF NOT EXISTS notificador_db;
CREATE USER admin_notificador_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON notificador_db.* TO 'admin_notificador_db'@'localhost';

CREATE DATABASE IF NOT EXISTS suscripciones_db;
CREATE USER admin_suscripciones_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON suscripciones_db.* TO 'admin_suscripciones_db'@'localhost';

CREATE DATABASE IF NOT EXISTS usuarios_db;
CREATE USER admin_usuarios_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON usuarios_db.* TO 'admin_usuarios_db'@'localhost';

CREATE DATABASE IF NOT EXISTS valoraciones_db;
CREATE USER admin_valoraciones_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON valoraciones_db.* TO 'admin_valoraciones_db'@'localhost';

CREATE DATABASE IF NOT EXISTS logos_db;
CREATE USER admin_logos_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON logos_db.* TO 'admin_logos_db'@'localhost';

CREATE DATABASE IF NOT EXISTS comunidades_db;
CREATE USER admin_comunidades_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON comunidades_db.* TO 'admin_comunidades_db'@'localhost';

FLUSH PRIVILEGES;
