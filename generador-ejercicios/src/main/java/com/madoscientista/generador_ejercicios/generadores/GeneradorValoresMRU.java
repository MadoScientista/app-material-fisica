package com.madoscientista.generador_ejercicios.generadores;

import java.util.Random;

import com.madoscientista.generador_ejercicios.model.DatosEjercicio;
import com.madoscientista.generador_ejercicios.model.Dato;
import com.madoscientista.generador_ejercicios.model.ContextoFisico;
import com.madoscientista.generador_ejercicios.model.UnidadDeMedida;
import com.madoscientista.generador_ejercicios.service.UnidadDeMedidaService;
import com.madoscientista.generador_ejercicios.service.VariableFisicaService;
import com.madoscientista.generador_ejercicios.utilidades.UtilidadVariables;


public class GeneradorValoresMRU {

    // Prueba para conversión de unidades
    private UnidadDeMedida km;
    private UnidadDeMedida m;
    private UnidadDeMedida ms;
    private UnidadDeMedida kmh;
    private UnidadDeMedida min;
    private UnidadDeMedida s;
    //private UnidadDeMedida h;
    
    private UnidadDeMedidaService umService;
    private VariableFisicaService vfService;
    
    private static final Random random = new Random();

    public GeneradorValoresMRU(UnidadDeMedidaService umService, VariableFisicaService vfService){
        
        this.vfService = vfService;
        this.umService = umService;
        
    }


    // Genera un objeto DatosEjercicio con variables x, x0, v y t
    // con valores seguún contexto y dificultad
    public DatosEjercicio generarValores(String nombreIncognita, String nombreDificultad, ContextoFisico contexto, boolean resultadoPositivo){

        // Busca unidades de medida base en el SI
        km = umService.getBySimbolo("km");
        m = umService.getBySimbolo("m");
        ms = umService.getBySimbolo("m/s");
        kmh = umService.getBySimbolo("km/h");
        s = umService.getBySimbolo("s");
        min = umService.getBySimbolo("min");
        //h = umService.getBySimbolo("h");

        // Crea datos con unidad de medida y asignación de tipo de variable
        // Por ahora se asigna manualmente la unidad base del SI
        Dato x = new Dato();
        //x.setUnidadDeMedida(m);
        x.setVariable(vfService.getBySimbolo("x"));
        x.setUnidadDeMedida(m);
        
        Dato x0 = new Dato();
        //x0.setUnidadDeMedida(m);
        x0.setVariable(vfService.getBySimbolo("x0"));
        x0.setUnidadDeMedida(m);

        Dato v = new Dato();
        //v.setUnidadDeMedida(ms);
        v.setVariable(vfService.getBySimbolo("v"));
        v.setUnidadDeMedida(ms);

        Dato t = new Dato();
        //t.setUnidadDeMedida(s);
        t.setVariable(vfService.getBySimbolo("t"));
        t.setUnidadDeMedida(s);


        // Declara el objeto a retornar
        DatosEjercicio datosCalculados;

        // Modifica las variables dependiendo de la incógnita a calcular
        datosCalculados = switch(nombreIncognita){
            case "POSICION_INICIAL" -> calcularX0(x, x0, v, t, contexto, nombreDificultad, resultadoPositivo);
            case "POSICION" -> calcularX(x, x0, v, t, contexto, nombreDificultad, resultadoPositivo);
            case "VELOCIDAD" -> calcularV(x, x0, v, t, contexto, nombreDificultad, resultadoPositivo);
            case "TIEMPO" -> calcularT(x, x0, v, t, contexto, nombreDificultad, resultadoPositivo);
            default -> new DatosEjercicio();
        };

        // Agrega los datos calculados el tipo de variable física incógnita
        datosCalculados.setIncognita(vfService.getBySimbolo(nombreIncognita));
        return datosCalculados;
    }


    // Genera un objeto Datos ejercicios y agrega los datos x, x0, v y t
    private DatosEjercicio build(Dato x, Dato x0, Dato v, Dato t){
        DatosEjercicio datos = new DatosEjercicio();
        datos.agregarDato(x);
        datos.agregarDato(x0);
        datos.agregarDato(v);
        datos.agregarDato(t);
        
        return datos;
    }

    //-----------------------------------------------------------------------------------------------------
    //--------------------------------| CALCULAR POSICIÓN INICIAL X0 |-------------------------------------
    //
    // Realiza cálculos con x0 como incógnita
    //
    //-----------------------------------------------------------------------------------------------------

    private DatosEjercicio calcularX0(Dato x, Dato x0, Dato v, Dato t, ContextoFisico contexto, String nombreDificultad, boolean resultadoPositivo){
        // x0 = x - v*t

        // Inicialización de variables de valdiación según dificultad
        boolean variablesValidas = true;
        boolean xValido;
        boolean x0Valido;
        boolean vValido;

        // Cálculo de rango temporal en segundos
        int tMin = Math.round(contexto.getXMin()/contexto.getVMin());
        int tMax = Math.round(contexto.getXMax()/contexto.getVMax());

        int rangoT = tMax - tMin;

        // Ciclo de cálculo de valores según nivel de dificultad
        do{ 

            xValido = true;
            x0Valido = true;
            vValido = true;

            // Cálculo de tiempo aleatorio
            t.setValor(tMin + random.nextInt(rangoT));

            // Dificultad elemental y media utilizan valores enteros
            if(nombreDificultad.equals("ELEMENTAL") || nombreDificultad.equals("INTERMEDIO")){
                v = UtilidadVariables.randomInt(v, contexto.getVMin(), contexto.getVMax(), resultadoPositivo);
                x = UtilidadVariables.randomInt(x, contexto.getXMin(), contexto.getXMax(),  true);
            }

            // Dificultad avanzada calcula al menos un valor decimal
            if(nombreDificultad.equals("AVANZADO") ){
                if(random.nextBoolean()){
                    v = UtilidadVariables.randomInt(v, contexto.getVMin(), contexto.getVMax(), resultadoPositivo);
                    x = UtilidadVariables.randomDouble(x, contexto.getXMin(), contexto.getXMax(), true, 2);
                }else{
                    x = UtilidadVariables.randomInt(x, contexto.getXMin(), contexto.getXMax(),  true);
                    v = UtilidadVariables.randomDouble(v, contexto.getVMin(), contexto.getVMax(),  resultadoPositivo, 2);
                }
            }

            // Cálculo de incognita según ecuación MRU
            x0.setValor(x.getValor() - v.getValor()*t.getValor());

            // Para dificultades intermedias y avanzadas se realizan conversión de unidades
            if(nombreDificultad.equals("INTERMEDIO") || nombreDificultad.equals("AVANZADO")){

                // Seteamos las unidades en el sistema internacional para evitar
                // cambios de unidades debido a la vuelta en ciclos
                v.setUnidadDeMedida(ms);
                x.setUnidadDeMedida(m);

                if(random.nextBoolean()){
                    Dato vConvertido = UtilidadVariables.convertirUnidad(v, kmh);
                    if(UtilidadVariables.comprobarDecimales(vConvertido, 2) && vConvertido.getValor() != 0){
                        v = vConvertido;
                    }else{
                        vValido = false;
                    }
                }else{
                    Dato xConvertido = UtilidadVariables.convertirUnidad(x, km);  
                    if(UtilidadVariables.comprobarDecimales(xConvertido, 3)){
                        x = xConvertido;
                    }else{
                        xValido = false;
                    }
                }
            }

            // Para que los cálculos no sean extremadamente simples la posición final no
            // debe ser igual a cero o igual al tiempo de movimiento
            x0Valido = x0.getValor() != 0 && x0.getValor() != t.getValor() && UtilidadVariables.comprobarDecimales(x0, 3) ;


            // x debe estar dentro de los valores xmin y xmax del contexto
            
            // Validación para casos con resultado positivo/negativo
            if(resultadoPositivo){
                x0Valido = x0Valido && x0.getValor() > 0 && x0.getValor() >= contexto.getXMax();
            }else{
                x0Valido = x0Valido && x0.getValor() < 0 && x0.getValor() >= contexto.getXMax()*-1;
            }

            //Se deben cumplir todas las validaciones para salir del ciclo
            variablesValidas = xValido && x0Valido && vValido;

        }while(!variablesValidas);

        // Las variables calculadas son retornadas en un objeto DatosEjercicio con build()
        return build(x, x0, v, t);

    }

    //-----------------------------------------------------------------------------------------------------
    //--------------------------------| CALCULAR POSICIÓN FINAL X |----------------------------------------
    //-----------------------------------------------------------------------------------------------------
  
    private DatosEjercicio calcularX(Dato x, Dato x0, Dato v, Dato t, ContextoFisico contexto, String nombreDificultad, boolean resultadoPositivo){
        // x = x0 + v*t

        // Inicialización de variables de valdiación según dificultad
        boolean variablesValidas = true;
        boolean xValido;
        boolean x0Valido;
        boolean vValido;
        boolean tValido;

        // Cálculo de rango temporal en segundos
        int tMin = Math.round(contexto.getXMin()/contexto.getVMin());
        int tMax = Math.round(contexto.getXMax()/contexto.getVMax());

        int rangoT = tMax - tMin;

        // Ciclo de cálculo de valores según nivel de dificultad
        do{ 

            xValido = true;
            x0Valido = true;
            vValido = true;
            tValido = true;

            // Cálculo de tiempo aleatorio
            t.setValor(tMin + random.nextInt(rangoT));

            // Dificultad elemental y media utilizan valores enteros
            if(nombreDificultad.equals("ELEMENTAL") || nombreDificultad.equals("INTERMEDIO")){
                v = UtilidadVariables.randomInt(v, contexto.getVMin(), contexto.getVMax(), resultadoPositivo);
                x0 = UtilidadVariables.randomInt(x0, contexto.getXMin(), contexto.getXMax(),  true);
            }

            // Dificultad avanzada calcula al menos un valor decimal
            if(nombreDificultad.equals("AVANZADO") ){
                if(random.nextBoolean()){
                    v = UtilidadVariables.randomInt(v, contexto.getVMin(), contexto.getVMax(), resultadoPositivo);
                    x0 = UtilidadVariables.randomDouble(x0, contexto.getXMin(), contexto.getXMax(), true, 2);
                }else{
                    x0 = UtilidadVariables.randomInt(x0, contexto.getXMin(), contexto.getXMax(),  true);
                    v = UtilidadVariables.randomDouble(v, contexto.getVMin(), contexto.getVMax(),  resultadoPositivo, 2);
                }
            }

            // Cálculo de incognita según ecuación MRU
            x.setValor(x0.getValor() + v.getValor()*t.getValor());

            // Para dificultades intermedias y avanzadas se realizan conversión de unidades
            if(nombreDificultad.equals("INTERMEDIO") || nombreDificultad.equals("AVANZADO")){

                // Seteamos las unidades en el sistema internacional para evitar
                // cambios de unidades debido a la vuelta en ciclos
                v.setUnidadDeMedida(ms);
                x0.setUnidadDeMedida(m);

                if(random.nextBoolean()){
                    Dato vConvertido = UtilidadVariables.convertirUnidad(v, kmh);
                    if(UtilidadVariables.comprobarDecimales(vConvertido, 2) && vConvertido.getValor() != 0){
                        v = vConvertido;
                    }else{
                        vValido = false;
                    }
                }else{
                    Dato x0Convertido = UtilidadVariables.convertirUnidad(x0, km);  
                    if(UtilidadVariables.comprobarDecimales(x0Convertido, 3)){
                        x0 = x0Convertido;
                    }else{
                        x0Valido = false;
                    }
                }

                if(nombreDificultad.equals("AVANZADO")){
                    Dato tConvertido = UtilidadVariables.convertirUnidad(t, min);  
                    if(UtilidadVariables.comprobarDecimales(tConvertido, 2)){
                        t = tConvertido;
                    }else{
                        tValido = false;
                    }
                }
            }

            // Para que los cálculos no sean extremadamente simples la posición final no
            // debe ser igual a cero o igual al tiempo de movimiento
            xValido = x.getValor() != 0 && x.getValor() != t.getValor() && UtilidadVariables.comprobarDecimales(x, 3) ;


            // x debe estar dentro de los valores xmin y xmax del contexto
            
            // Validación para casos con resultado positivo/negativo
            if(resultadoPositivo){
                xValido = xValido && x.getValor() > 0 && x.getValor() >= contexto.getXMax();
            }else{
                xValido = xValido && x.getValor() < 0 && x.getValor() >= contexto.getXMax()*-1;
            }

            //Se deben cumplir todas las validaciones para salir del ciclo
            variablesValidas = xValido && x0Valido && vValido && tValido;

        }while(!variablesValidas);

        // Las variables calculadas son retornadas en un objeto DatosEjercicio con build()
        return build(x, x0, v, t);
    }

    //-----------------------------------------------------------------------------------------------------
    //-------------------------------------| CALCULAR VELOCIDAD V |----------------------------------------
    //-----------------------------------------------------------------------------------------------------

    private DatosEjercicio calcularV(Dato x, Dato x0, Dato v, Dato t, ContextoFisico contexto, String nombreDificultad, boolean resultadoPositivo){
        // v = (x-x0)/t

        // Inicialización de variables de valdiación según dificultad
        boolean variablesValidas = true;
        boolean xValido;
        boolean x0Valido;
        boolean vValido;

        
        // Cálculo de rango temporal en segundos
        int tMin = Math.round(contexto.getXMin()/contexto.getVMin());
        int tMax = Math.round(contexto.getXMax()/contexto.getVMax());

        int rangoT = tMax - tMin;

        // Ciclo de cálculo de valores según nivel de dificultad
        do{
            xValido = true;
            x0Valido = true;
            vValido = true;

            // Cálculo de tiempo aleatorio
            t.setValor(tMin + random.nextInt(rangoT));
            
            if(nombreDificultad.equals("ELEMENTAL") || nombreDificultad.equals("INTERMEDIO")){
                x = UtilidadVariables.randomInt(x, contexto.getXMin(), contexto.getXMax(), resultadoPositivo);
                x0 = UtilidadVariables.randomInt(x0, contexto.getXMin(), contexto.getXMax(), true);
            }

            // Cálculo de posiciones con decimales y posición inicial negativa/positiva
            if(nombreDificultad.equals("AVANZADO")){
                x = UtilidadVariables.randomDouble(x, contexto.getXMin(), contexto.getXMax(), resultadoPositivo, 3);
                x0 = UtilidadVariables.randomDouble(x0, contexto.getXMin(), contexto.getXMax(), !resultadoPositivo, 3);
            }

            // Cálculo de la velocidad
            v.setValor((x.getValor()-x0.getValor())/t.getValor());

            // Validación decimales para el resultado final de la velocidad en el SI
            if(nombreDificultad.equals("ELEMENTAL") || nombreDificultad.equals("INTERMEDIO")){
                if(!UtilidadVariables.comprobarDecimales(v, 0)){
                    vValido = false;
                }
            }

            if(nombreDificultad.equals("AVANZADO")){
                if(!UtilidadVariables.comprobarDecimales(v, 2)){
                    vValido = false;
                }
            }


            // Conversión de unidades
            if(nombreDificultad.equals("INTERMEDIO") || nombreDificultad.equals("AVANZADO")){
                
                // Reseteamos unidades de medida
                x.setUnidadDeMedida(m);
                t.setUnidadDeMedida(s);

                // Conversión de unidades de tiempo o distancia
                if(random.nextBoolean()){
                    Dato xConvertido = UtilidadVariables.convertirUnidad(x, km);
                    Dato x0Convertido = UtilidadVariables.convertirUnidad(x0, km);
                    
                    if(UtilidadVariables.comprobarDecimales(xConvertido, 3) && UtilidadVariables.comprobarDecimales(x0Convertido, 3) ){
                        x = xConvertido;
                        x0 = x0Convertido;
                    }
                    
                }else{
                    Dato tConvertido = UtilidadVariables.convertirUnidad(t, min);
                    if(UtilidadVariables.comprobarDecimales(tConvertido, 2)){
                        t = tConvertido;
                    }
                    
                }
            }

            // Validación resultado positivo
            if(resultadoPositivo){
                vValido = vValido && v.getValor() > 0 && v.getValor() <= contexto.getVMax();
            }else{
                vValido = vValido && v.getValor() < 0 && v.getValor() >= contexto.getVMax()*-1;
            }

            variablesValidas = vValido && xValido && x0Valido;

        }while(!variablesValidas);

        return build(x, x0, v, t);
    }

    //-----------------------------------------------------------------------------------------------------
    //-------------------------------------| CALCULAR TIEMPO T |-------------------------------------------
    //-----------------------------------------------------------------------------------------------------

    private DatosEjercicio calcularT(Dato x, Dato x0, Dato v, Dato t, ContextoFisico contexto, String nombreDificultad, boolean resultadoPositivo){

        // t = (x-x0)/v

        return build(x, x0, v, t);
    }
}
