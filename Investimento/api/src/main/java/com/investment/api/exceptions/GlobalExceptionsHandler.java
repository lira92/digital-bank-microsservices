package com.investment.api.exceptions;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;
import com.investment.api.modules.moneyTransaction.exceptions.NoCashException;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> invalidForm(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, List<String>> response = Map.of("error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ NoSuchElementException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Map<String, String>> notFoundException(NoSuchElementException ex) {
        Map<String, String> response = Map.of("error", "Não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({ ExternalApiException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Map<String, String>> externalApiException(ExternalApiException ex) {
        Map<String, String> response = Map.of("error", "Erro ao se conectar com outros serviços");
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler({ NoCashException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> noCashException(NoCashException ex) {
        Map<String, String> response = Map.of("error", "Não há dinheiro suficiente para realizar essa operação");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
