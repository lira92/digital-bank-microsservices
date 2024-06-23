package br.com.biopark.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.biopark.exceptions.ExceptionResponse;
import br.com.biopark.exceptions.MinhaException;

@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(
            Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage());
        return new ResponseEntity<>(
                exceptionResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MinhaException.class)
    public final ResponseEntity<ExceptionResponse> handleMinhaException(
            MinhaException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getDescricao());
        return new ResponseEntity<>(
                exceptionResponse,
                HttpStatus.BAD_REQUEST);
    }
}
