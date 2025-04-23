package br.com.desafioresidencia.gerenciadoreventos.configs;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers, org.springframework.http.HttpStatus status,
                                                                 org.springframework.web.context.request.WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());

        // Retorna uma resposta HTTP com status 400 (BAD REQUEST) e a lista de erros
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

            }

            @ExceptionHandler(ConstraintViolationException.class)
            public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(e -> String.format("%s: %s", e.getPropertyPath(), e.getMessage()))
                .collect(Collectors.toList());
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }

            @Override
            protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                          org.springframework.http.HttpHeaders headers,
                                                                          org.springframework.http.HttpStatus status,
                                                                          org.springframework.web.context.request.WebRequest request) {
                        String error = "Requisicao mal formatada" + ex.getMostSpecificCause().getMessage();
                        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                    }
    }


