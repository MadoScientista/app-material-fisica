package com.madoscientista.generador_ejercicios.utilidades;

import java.util.Random;

import com.madoscientista.generador_ejercicios.model.Dato;
import com.madoscientista.generador_ejercicios.model.UnidadDeMedida;


// Clase con métodos estáticos para realizar operaciones con objetos de tipo Dato,
// como convertir unidades de medida, generar valores aleatorios y comprobar el número de decimales de un valor

public class UtilidadVariables {

    private static Random random = new Random();

    // Convierte el valor de un dato a la unidad de medida de salida
    // utilizando el factor de coversión a SI de ambas unidades de medida
    public static Dato convertirUnidad(Dato dato, UnidadDeMedida uSalida){        
        
        double valor = dato.getValor() * (dato.getUnidadDeMedida().getFactorConversionSI() / uSalida.getFactorConversionSI());
        return new Dato(
            dato.getVariable(),
            valor,
            uSalida
        );
    }


    // Retorna el dato de entrada con un valor entero aleatorio entre
    // min y max, con la posibilidad de ser positivo y negativo según el parámetro "positivo"
    public static Dato randomInt(Dato dato, int min, int max, boolean positivo){
        Dato nuevaDato = new Dato();

        int numeroRandom = random.nextInt((max - min + 1)) + min;
        nuevaDato.setValor(numeroRandom);

        nuevaDato.setValor((positivo) ? nuevaDato.getValor(): nuevaDato.getValor()*-1);
        nuevaDato.setVariable(dato.getVariable());
        nuevaDato.setUnidadDeMedida(dato.getUnidadDeMedida());
        

        return nuevaDato;   
    }


    // Retorna el dato de entrada con un valor decimal aleatorio entre
    // min y max, con la posibilidad de ser positivo y negativo según el parámetro "positivo"
    public static Dato randomDouble(Dato variable, int min, int max, boolean positivo, int nDecimales){
        Dato nuevaVariable = new Dato();

        double numeroRandom = random.nextDouble(((max - min) + min+1));
        double decimalRandom = Math.round(numeroRandom*Math.pow(10, nDecimales))/Math.pow(10, nDecimales);

        nuevaVariable.setValor(decimalRandom);
        
        nuevaVariable.setUnidadDeMedida(variable.getUnidadDeMedida());
        nuevaVariable.setVariable(variable.getVariable());
        nuevaVariable.setValor((positivo) ? nuevaVariable.getValor(): nuevaVariable.getValor()*-1);

        return nuevaVariable;   
    }


    // Comprueba si el valor del dato de entrada tiene como máximo el número
    // de decimales especificado en nDecimales
    public static boolean comprobarDecimales(Dato variable, int nDecimales){

        double n = variable.getValor()*Math.pow(10, nDecimales);
        double n_ceil = Math.ceil(variable.getValor()*Math.pow(10, nDecimales));
        boolean correcto = (n_ceil - n) == 0;

        return correcto;

        // double factor = Math.pow(10, nDecimales);
        // double valor = variable.getValor();

        // double redondeado = Math.round(valor * factor) / factor;

        // return Math.abs(valor - redondeado) < 1e-9;
    }
}
