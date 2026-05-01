
-- Poblado de tabla contexto_fisico
Insert into CONTEXTO_FISICO (ID_CONTEXTO_FISICO,M_MAX,M_MIN,NOMBRE,V_MAX,V_MIN,X_MAX,X_MIN) values ('1','100','50.0','PERSONA','6','2','600','20');
Insert into CONTEXTO_FISICO (ID_CONTEXTO_FISICO,M_MAX,M_MIN,NOMBRE,V_MAX,V_MIN,X_MAX,X_MIN) values ('2','5000','1000.0','AUTOMOVIL','30','10','1000','100');
Insert into CONTEXTO_FISICO (ID_CONTEXTO_FISICO,M_MAX,M_MIN,NOMBRE,V_MAX,V_MIN,X_MAX,X_MIN) values ('3','10000','4000.0','TREN','50','20','4000','1000');
Insert into CONTEXTO_FISICO (ID_CONTEXTO_FISICO,M_MAX,M_MIN,NOMBRE,V_MAX,V_MIN,X_MAX,X_MIN) values ('4','25000','10000.0','AVION','230','40','10000','5000');
Insert into CONTEXTO_FISICO (ID_CONTEXTO_FISICO,M_MAX,M_MIN,NOMBRE,V_MAX,V_MIN,X_MAX,X_MIN) values ('5','0.12','0.1','BALA','110','85','2000','400');


-- Poblado de tabla dificultad
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('21','Operaciones con números enteros','ELEMENTAL');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('22','Operaciones con conversión de unidades','INTERMEDIO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('23','Conversión de unidades y resultado con decimales','AVANZADO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('1','Operaciones con números enteros y resultado positivo','ELEMENTAL_POSITIVO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('2','Operaciones con números enteros y resultado negativo','ELEMENTAL_NEGATIVO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('3','Operaciones con conversión de unidades','INTERMEDIO_POSITIVO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('4','Operaciones con conversión de unidades y resultado negativo','INTERMEDIO_NEGATIVO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('5','Conversión de unidades, operación con decimales y resultado positivo','AVANZADO_POSITIVO');
Insert into DIFICULTAD (ID_DIFICULTAD,DESCRIPCION,NOMBRE) values ('6','Conversión de unidades, operación con decimales y resultado negativo','AVANZADO_NEGATIVO');

-- Poblado de tabla magnitud_fisica
Insert into MAGNITUD_FISICA (ID_MAGNITUD_FISICA,NOMBRE,SIMBOLO) values ('1','LONGITUD','L');
Insert into MAGNITUD_FISICA (ID_MAGNITUD_FISICA,NOMBRE,SIMBOLO) values ('2','TIEMPO','t');
Insert into MAGNITUD_FISICA (ID_MAGNITUD_FISICA,NOMBRE,SIMBOLO) values ('3','VELOCIDAD','L/t');
Insert into MAGNITUD_FISICA (ID_MAGNITUD_FISICA,NOMBRE,SIMBOLO) values ('4','ACELERACION','L/t2');

-- Poblado de tabla tema_fisica
Insert into TEMA_FISICA (ID_TEMA_FISICA,DESCRIPCION,NOMBRE) values ('1','Movimiento Rectilíneo Uniforme','MRU');
Insert into TEMA_FISICA (ID_TEMA_FISICA,DESCRIPCION,NOMBRE) values ('2','Movimiento Rectilíneo Uniforme Acelerado','MRUA');

-- Poblado de tabla unidad_de_medida
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('1',1,'1.0','Metro','m','1',1);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('2',1,'1000.0','Kilómetro','km','1',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('3',1,'0.01','Centímetro','cm','1',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('4',0,'0.3048','Pie','ft','1',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('5',1,'1.0','Segundo','s','2',1);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('6',0,'60.0','Minuto','min','2',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('7',0,'3600.0','Hora','h','2',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('8',1,'1.0','Metro por segundo','m/s','3',1);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('9',0,'0.27778','Kilómetro por hora','km/h','3',0);
Insert into UNIDAD_DE_MEDIDA (ID_UNIDAD_DE_MEDIDA,ESSI,FACTOR_CONVERSIONSI,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA,ES_BASESI) values ('10',1,'1.0','Metro por segundo cuadrado','m/s2','4',1);

-- Poblado de tabla variable_fisica
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('1','POSICION_INICIAL','x0','1');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('2','POSICION','x','1');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('3','DISTANCIA','d','1');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('4','TIEMPO','t','2');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('5','TIEMPO_INICIAL','t0','2');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('6','VELOCIDAD','v','3');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('7','VELOCIDAD_INICIAL','v0','3');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('8','VELOCIDAD_FINAL','vf','3');
Insert into VARIABLE_FISICA (ID_VARIABLE_FISICA,NOMBRE,SIMBOLO,ID_MAGNITUD_FISICA) values ('9','ACELERACION','a','4');

-- Modifcación de tinytext a text
ALTER TABLE plantilla_enunciado MODIFY COLUMN enunciado TEXT NOT NULL;

-- Poblado de tabla plantilla_enunciado
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('1','1','2','1',1, 'Una persona se desplaza en línea recta con velocidad constante de {v}. Inicialmente se encuentra en la posición {x0} y mantiene su movimiento durante {t}. ¿Cuál será su posición final? Exprese su respuesta en unidades del Sistema Internacional.');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('2','1','2','1',1, 'Durante una tarde tranquila, una persona camina en línea recta a lo largo de una vereda, desplazándose con una velocidad constante de {v}. Si al comienzo estaba en la posición {x0} y mantiene ese movimiento durante {t}, ¿En qué posición se encontrará al final del recorrido?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('3','1','2','1',1, 'En una mañana despejada, una persona recorre un camino recto dentro de un parque, avanzando con una velocidad constante de {v}. Si al iniciar su recorrido se encontraba en la posición {x0} y continúa desplazándose durante {t}. ¿En qué posición se encontrará al final del recorrido?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('4','1','2','1',1, 'Al atardecer, una persona camina por una avenida recta manteniendo una velocidad constante de {v}. Si en el instante inicial estaba en la posición {x0} y sigue avanzando durante {t}. ¿Cuál será su posición al término de ese intervalo de tiempo?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('5','1','2','1',1, 'Martina está paseando a su perro por un sendero rectilíneo en el bosque. Ella inicia su cronómetro justo cuando se encuentra a {x0} de un gran roble que marca el inicio del camino. Si camina con paso firme a una velocidad constante de {v} durante {t}, ¿cuál será la posición final de Martina respecto al roble?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('6','1','2','1',1, 'Un corredor está realizando una prueba de control. Cruza la marca de los {x0} de la pista manteniendo una técnica perfecta y una velocidad constante de {v}. El entrenador detiene la observación tras {t} de recorrido. ¿En qué posición de la pista se encuentra el atleta en ese instante?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('7','1','2','1',1, 'Después de salir de la escuela, Juan camina hacia su casa por una avenida principal. En un momento dado, se encuentra a {x0} del semáforo de la esquina y decide mantener un ritmo constante de {v} para llegar a tiempo a almorzar. Si mantiene ese ritmo durante {t}, ¿a qué distancia del semáforo se encontrará?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('8','1','2','1',1, 'Una turista consulta su mapa y descubre que está a {x0} de la oficina de información turística. Para llegar a la plaza principal, debe seguir derecho por la misma calle. Comienza a caminar a una velocidad de {v} y avanza sin detenerse durante {t}. ¿Cuál es la ubicación (posición) de la turista respecto a la oficina de información al final de su caminata?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('9','1','2','1',1, 'Una persona está cumpliendo su meta diaria de pasos. Al revisar su aplicación, nota que ya ha recorrido {x0} desde su punto de partida. Si decide mantener un paso constante de {v} durante los próximos {t} para enfriar los músculos, ¿cuál será su posición final total respecto al inicio?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('10','1','2','1',1, 'Un deportista recorre orilla de una playa rectilínea. Al iniciar su cronómetro, ya ha dejado atrás el muelle a una distancia de {x0}. Si mantiene un ritmo constante de {v} durante un tiempo de {t}, ¿cuál será su posición final con respecto al muelle al detenerse?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('11','1','2','1',1, 'Comienza a lloviznar y una persona decide apurar el paso para llegar a un refugio. En el momento en que empieza a contar el tiempo, ya se encuentra a {x0} de su oficina. Si camina rápidamente a una velocidad constante de {v} durante {t}, ¿en qué posición respecto a su oficina se encontrará cuando deje de cronometrar?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('12','1','2','1',1, 'Una persona se desplaza por un camino rural recto que conecta dos pueblos. Al comenzar a medir el tiempo, se encuentra a {x0} de la señal de salida del primer pueblo. Si avanza con una velocidad constante de {v} durante un intervalo de {t}, ¿en qué posición respecto a la señal de salida se encontrará al finalizar ese tiempo?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('13','1','2','1',0, 'Un tren de carga se desplaza por una vía rectilínea. Al comenzar el monitoreo, su posición es de {x0} respecto a la estación central. Si mantiene una velocidad de {v} durante un tiempo de {t}, ¿cuál será su posición final en la vía respecto a la estación?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('14','1','2','1',0, 'Un senderista camina por un trayecto rectilíneo en la montaña. Al comenzar a medir el tiempo, su posición es de {x0} respecto a un refugio que sirve como punto de referencia. Si se desplaza con una velocidad constante de {v} durante {t}, ¿cuál será su posición final respecto al refugio?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('15','1','2','1',0, 'Un operario se desplaza por un pasillo técnico de una planta industrial de grandes dimensiones. Si inicialmente se encuentra en la posición {x0} respecto a la entrada principal y mantiene una velocidad constante de {v} durante {t}, ¿en qué posición respecto a la entrada se encontrará al término de ese tiempo?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('16','1','2','1',0, 'Una persona trota por el borde de una carretera recta de largo alcance. Al marcar el inicio del ejercicio, su posición es de {x0} respecto a un hito kilométrico específico. Si mantiene una velocidad constante de {v} durante {t}, ¿cuál será su posición final respecto a dicho hito?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('17','1','6','1',1, 'Una persona camina por una pista rectilínea partiendo desde la posición {x0}. Luego de avanzar a paso constante durante un tiempo de {t}, se encuentra en la posición {x}. ¿Cuál fue la velocidad de la persona durante el movimiento?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('18','1','6','1',1, 'Un excursionista se desplaza por un sendero recto. Al iniciar el cronómetro, su posición es de {x0} respecto a una señal en el camino, y al cabo de {t}, su posición es de {x}. Si el excursionista se desplazó a paso constante ¿Cuál fue su velocidad?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('19','1','6','1',0, 'Una trabajadora se mueve por un muelle de carga en línea recta. Si en el instante inicial se encuentra en la posición {x0} y tras {t} de caminata su posición es {x}, ¿cuál es la velocidad media de su desplazamiento?');
Insert into PLANTILLA_ENUNCIADO (ID_PLANTILLA_ENUNCIADO,ID_CONTEXTO_FISICO,ID_INCOGNITA,ID_TEMA_FISICA,RESULTADO_POSITIVO, enunciado) values ('20','1','6','1',0, 'Una persona trota por una calle recta manteniendo un ritmo uniforme. Si comienza a {x0} de su casa y después de un intervalo de {t} se ubica en la posición {x}, ¿qué velocidad mantuvo durante el recorrido?');


