package com.azul.isaacscardoso.azulhelpdeskappbackend.api.exceptions;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectDataNotFoundException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectUniqueDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public APIExceptionHandler() { }

    @Autowired
    public APIExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected List<APIFieldValidationException.Field> getInvalidFields(MethodArgumentNotValidException exception) {
        List<APIFieldValidationException.Field> fields = new ArrayList<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = this.messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new APIFieldValidationException.Field(name, message));
        }
        return fields;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        APIFieldValidationException fieldValidationException = new APIFieldValidationException();
        fieldValidationException.setFields(getInvalidFields(ex));
        fieldValidationException.setStatus(status.value());
        fieldValidationException.setDateHour(LocalDateTime.now());
        fieldValidationException.setTitle("Valores inválidos!");

        return handleExceptionInternal(ex, fieldValidationException, headers, status, request);
    }

    @ExceptionHandler(ObjectDataNotFoundException.class)
    protected ResponseEntity<APIError> objectDataNotFoundException(
            ObjectDataNotFoundException exception, HttpServletRequest request) {

        APIError error = new APIError(
                HttpStatus.NOT_FOUND.value(),
                exception.getTitle(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ObjectUniqueDataException.class)
    protected ResponseEntity<APIError> objectUniqueDataException(
            ObjectUniqueDataException exception, HttpServletRequest request) {

        APIError error = new APIError(
                HttpStatus.CONFLICT.value(),
                exception.getTitle(),
                exception.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
