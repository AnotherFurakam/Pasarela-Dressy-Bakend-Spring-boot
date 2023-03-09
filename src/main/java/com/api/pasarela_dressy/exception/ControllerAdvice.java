package com.api.pasarela_dressy.exception;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.ErrorDto;
import com.api.pasarela_dressy.exception.GlobalException;
import com.api.pasarela_dressy.exception.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler
{

    @ExceptionHandler(value = UniqueFieldException.class)
    public ResponseEntity<UniqueErrorDto> uniqueFieldExeptionHandler(UniqueFieldException ex) {
        UniqueErrorDto error = UniqueErrorDto.builder().status(ex.getStatus()).message(ex.getErrors()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundExeptionHandler(NotFoundException ex){
        ErrorDto error = ErrorDto.builder().message(ex.getMessage()).status(ex.getStatus()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDto> badRequestExeptionHandler(BadRequestException ex){
        ErrorDto error = ErrorDto.builder().message(ex.getMessage()).status(ex.getStatus()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity<ErrorDto> globalExceptionHandler(GlobalException globalException)
    {
        ErrorDto error = ErrorDto
            .builder()
            .message(globalException.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    //Global handle error for validation
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request)
    {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldError = result.getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError error:
             fieldError) {
            errors.add(error.getDefaultMessage());
        }

        return new ResponseEntity<>(ValidationErrorDto.builder().message("Validation field error").fieldErrors(errors).build(),status);
    }
}
