package com.madoscientista.usuarios.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.madoscientista.usuarios.dto.errorDTO.ResponseErrorDTO;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Controla el error por nombre de usuario o correo duplicado
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseErrorDTO> handleDuplicateKey(DataIntegrityViolationException ex){
        
        // Captura el mensaje específico de error para ver cuál es el campo en conflicto
        String mensajeError = ex.getMostSpecificCause().getMessage().toLowerCase();
        String campo = "";
        String mensaje = "";
        int codigo = HttpStatus.CONFLICT.value();

        if(mensajeError.contains("uk_usuario_nombre_usuario")){
            campo = "nombreUsuario";
            mensaje = "El nombre de usuario ya existe en la plataforma";
        }else if(mensajeError.contains("uk_usuario_email")){
            campo = "email";
            mensaje = "El email ingresado ya existe en la plataforma";
        }

        ResponseErrorDTO error = new ResponseErrorDTO();
        error.setCampo(campo);
        error.setCodigo(codigo);
        error.setMensaje(mensaje);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Controlla errores por comunicación entre ms con Feign
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ResponseErrorDTO> handleFeignException(FeignException e){
        
        // Captura el mensaje de error
        String mensajeError = e.getMessage();
        String campo = "";
        String mensaje = "Error de comunicación con microservicio";

        // Si no hay un código válido retorna siempre 503 (Service Unavailable)
        // Me daba -1 si el microservicio no estaba levantado
        int codigo = e.status() > 0 ? e.status():503;

        // Si contiene el puerto del ms suscripciones
        if(mensajeError.contains("8081")){ // Si contiene el puerto del ms generador-ejercicios
            campo = "Generador de ejercicios";
        }else if(mensajeError.contains("8082")){ // Si contiene el puerto del ms generador-ejercicios
            campo = "Historial";
        }else if(mensajeError.contains("8083")){ // Si contiene el puerto del ms generador-ejercicios
            campo = "Logros";
        }else if(mensajeError.contains("8086")){ // Si contiene el puerto del ms generador-ejercicios
            campo = "Suscripciones";
        }else{ // Por si olvido algún microservicio por ahí
            campo = "Microservicio";
        }

        ResponseErrorDTO error = new ResponseErrorDTO();
        error.setCampo(campo);
        error.setMensaje(mensaje);
        error.setCodigo(codigo);

        return ResponseEntity.status(codigo).body(error);
    }


    // Controla los errores ocurridos con el microservicio de suscripciones
    @ExceptionHandler(SuscripcionesException.class)
    public ResponseEntity<ResponseErrorDTO> handlesuscripcionesException(SuscripcionesException e){
        String mensaje = e.getMessage();
        String campo = "Suscripciones";
        int codigo = 503;
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        
        // En el caso de que el máximo de ejercicios sea null
        if(mensaje.contains("null")){
            status = HttpStatus.BAD_GATEWAY;
            codigo = 502;
        }

        // En el caso de que el máximo de ejercicios permitidos se haya alcanzado
        if(mensaje.contains("alcanzado")){
            status = HttpStatus.FORBIDDEN;
            codigo = 403;
        }

        ResponseErrorDTO error = new ResponseErrorDTO();
        error.setCampo(campo);
        error.setCodigo(codigo);
        error.setMensaje(mensaje);

        return ResponseEntity.status(status).body(error);
    }
}
