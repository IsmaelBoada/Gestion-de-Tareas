package com.portafolio.gtareas.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.portafolio.gtareas.rest.models.ModelResponseDto;
import com.portafolio.gtareas.services.exception.ApiException;
import com.portafolio.gtareas.services.messages.ApiMessages;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;


@RestControllerAdvice
public class AdviceRestController extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ModelResponseDto response = ModelResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(ApiMessages.ERROR_VALIDACIONES_REQUEST + String.join(", ", errors))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "";

        if (ex.getCause() instanceof InvalidFormatException ife &&
                ife.getTargetType().isEnum()) {
            message = ApiMessages.ERROR_ESTADO_TAREA + ife.getTargetType().getSimpleName()
                    + ". Valores permitidos: "
                    + String.join(", ",
                    Arrays.stream(ife.getTargetType().getEnumConstants())
                            .map(Object::toString)
                            .toList());
        }

        ModelResponseDto response = ModelResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ModelResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";

        String message;
        if (ex.getRequiredType() != null && ex.getRequiredType().equals(java.util.UUID.class)) {
            message = ApiMessages.ERROR_TOKEN_NO_VALIDO + field + ApiMessages.ERROR_UUID_NO_VALIDO + value;
        } else {
            message = ApiMessages.ERROR_NO_ESPERADO;
        }

        ModelResponseDto response = ModelResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = ApiException.class)
    public ModelResponseDto handleApiAccess(ApiException exception) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE)
                .message(exception.getMessage())
                .build();
    }


}
