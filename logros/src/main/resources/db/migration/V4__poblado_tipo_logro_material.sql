-- Inserta tipos de logro relacionados al microservicio material

INSERT INTO tipo_logro(nombre, descripcion, criterio, operador, umbral) 
VALUES ("Apuntes propios", "Crea tu primer item de ejercicio", "nItemsCreados", ">=", 1);

INSERT INTO tipo_logro(nombre, descripcion, criterio, operador, umbral) 
VALUES ("Biblioteca", "Crea 5 items de ejercicio", "nItemsCreados", ">=", 5);

INSERT INTO tipo_logro(nombre, descripcion, criterio, operador, umbral) 
VALUES ("Compilador", "Crea tu primer material didactico", "nMaterialesCreados", ">=", 1);
