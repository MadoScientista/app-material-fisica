CREATE DATABASE generador_ejercicios_db;
CREATE USER admin_generador_ejercicios_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON generador_ejercicios_db.* TO 'admin_generador_ejercicios_db'@'localhost';

CREATE DATABASE historial_db;
CREATE USER admin_historial_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON historial_db.* TO 'admin_historial_db'@'localhost';

CREATE DATABASE logros_db;
CREATE USER admin_logros_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON logros_db.* TO 'admin_logros_db'@'localhost';

CREATE DATABASE material_db;
CREATE USER admin_material_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON material_db.* TO 'admin_material_db'@'localhost';

CREATE DATABASE notificador_db;
CREATE USER admin_notificador_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON notificador_db.* TO 'admin_notificador_db'@'localhost';

CREATE DATABASE suscripciones_db;
CREATE USER admin_suscripciones_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON suscripciones_db.* TO 'admin_suscripciones_db'@'localhost';

CREATE DATABASE usuarios_db;
CREATE USER admin_usuarios_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON usuarios_db.* TO 'admin_usuarios_db'@'localhost';

CREATE DATABASE valoraciones_db;
CREATE USER admin_valoraciones_db@localhost IDENTIFIED BY "1234";
GRANT ALL PRIVILEGES ON valoraciones_db.* TO 'admin_valoraciones_db'@'localhost';

FLUSH PRIVILEGES;
